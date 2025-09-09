package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.CategoryRepository
import com.expenseecho.data.repository.BudgetRepository
import com.expenseecho.service.SmsReaderService
import com.expenseecho.data.entity.Category
import com.expenseecho.data.analytics.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

data class DashboardUiState(
    val totalIncome: Long = 0L,
    val totalExpenses: Long = 0L,
    val netAmount: Long = 0L,
    val savingsRate: Float = 0f,
    val categorySpending: List<CategorySpending> = emptyList(),
    val recentTransactions: Int = 0,
    val currentMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val atAGlanceSummary: AtAGlanceSummary? = null,
    val monthlyAnalytics: MonthlyAnalytics? = null,
    val customGraphs: List<CustomGraphConfig> = emptyList(),
    val showAddGraphDialog: Boolean = false,
    val transactionFilter: TransactionTypeFilter = TransactionTypeFilter.ALL_EXPENSES,
    val isRefreshing: Boolean = false
)

data class CategorySpending(
    val category: Category,
    val amount: Long,
    val percentage: Float
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetRepository: BudgetRepository,
    private val analyticsEngine: AnalyticsEngine,
    private val smsReaderService: SmsReaderService
) : ViewModel() {
    
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    private val _customGraphs = MutableStateFlow<List<CustomGraphConfig>>(emptyList())
    private val _showAddGraphDialog = MutableStateFlow(false)
    private val _transactionFilter = MutableStateFlow(TransactionTypeFilter.ALL_EXPENSES)
    private val _isRefreshing = MutableStateFlow(false)
    
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()
    
    val uiState: StateFlow<DashboardUiState> = combine(
        _currentMonth,
        categoryRepository.getAllCategories(),
        _customGraphs,
        _showAddGraphDialog,
        _transactionFilter
    ) { month, categories, customGraphs, showAddGraphDialog, transactionFilter ->
        val isRefreshing = _isRefreshing.value
        calculateDashboardData(month, categories, customGraphs, showAddGraphDialog, transactionFilter, isRefreshing)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState(isLoading = true)
    )
    
    fun changeMonth(month: YearMonth) {
        _currentMonth.value = month
    }
    
    fun goToPreviousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }
    
    fun goToNextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }
    
    fun showAddGraphDialog() {
        _showAddGraphDialog.value = true
    }
    
    fun hideAddGraphDialog() {
        _showAddGraphDialog.value = false
    }
    
    fun addCustomGraph(config: CustomGraphConfig) {
        val currentGraphs = _customGraphs.value.toMutableList()
        currentGraphs.add(config)
        _customGraphs.value = currentGraphs
    }
    
    fun removeCustomGraph(graphId: String) {
        val currentGraphs = _customGraphs.value.toMutableList()
        currentGraphs.removeAll { it.id == graphId }
        _customGraphs.value = currentGraphs
    }
    
    fun setTransactionFilter(filter: TransactionTypeFilter) {
        _transactionFilter.value = filter
    }
    
    fun refreshTransactions() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                android.util.Log.d("DashboardViewModel", "🔄 Manual refresh triggered")
                // Refresh recent SMS messages
                smsReaderService.refreshRecentTransactions()
                // Trigger a refresh by updating the current month (this will recalculate all data)
                val currentMonth = _currentMonth.value
                _currentMonth.value = currentMonth
            } catch (e: Exception) {
                android.util.Log.e("DashboardViewModel", "Error refreshing transactions: ${e.message}", e)
            } finally {
                _isRefreshing.value = false
            }
        }
    }
    
    private suspend fun calculateDashboardData(
        month: YearMonth,
        categories: List<Category>,
        customGraphs: List<CustomGraphConfig>,
        showAddGraphDialog: Boolean,
        transactionFilter: TransactionTypeFilter,
        isRefreshing: Boolean
    ): DashboardUiState {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        val totalIncome = transactionRepository.getTotalIncome(startDate, endDate)
        val totalExpenses = transactionRepository.getTotalExpenses(startDate, endDate)
        val netAmount = totalIncome - totalExpenses
        
        val savingsRate = if (totalIncome > 0) {
            (netAmount.toFloat() / totalIncome.toFloat()) * 100f
        } else 0f
        
        // Calculate category spending (Expense only, which now includes transfers)
        val categorySpending = mutableListOf<CategorySpending>()
        for (category in categories) {
            val amount = transactionRepository.getTotalByCategory(category.id, startDate, endDate)
            if (amount > 0) {
                val percentage = if (totalExpenses > 0) {
                    (amount.toFloat() / totalExpenses.toFloat()) * 100f
                } else 0f
                
                categorySpending.add(
                    CategorySpending(
                        category = category,
                        amount = amount,
                        percentage = percentage
                    )
                )
            }
        }
        
        // Add uncategorized transactions (Expense only, which now includes transfers)
        val uncategorizedAmount = transactionRepository.getTotalUncategorized(startDate, endDate)
        if (uncategorizedAmount > 0) {
            val uncategorizedCategory = Category(
                id = "uncategorized",
                name = "Uncategorized",
                isBudgetable = false,
                color = "#9E9E9E",
                icon = "help_outline"
            )
            
            val percentage = if (totalExpenses > 0) {
                (uncategorizedAmount.toFloat() / totalExpenses.toFloat()) * 100f
            } else 0f
            
            categorySpending.add(
                CategorySpending(
                    category = uncategorizedCategory,
                    amount = uncategorizedAmount,
                    percentage = percentage
                )
            )
        }
        
        // Sort by amount descending
        categorySpending.sortByDescending { it.amount }
        
        // Get analytics data
        val atAGlanceSummary = analyticsEngine.getAtAGlanceSummary(month)
        val monthlyAnalytics = analyticsEngine.getMonthlyAnalytics(month, transactionFilter)
        
        return DashboardUiState(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            netAmount = netAmount,
            savingsRate = savingsRate,
            categorySpending = categorySpending,
            currentMonth = month,
            isLoading = false,
            atAGlanceSummary = atAGlanceSummary,
            monthlyAnalytics = monthlyAnalytics,
            customGraphs = customGraphs,
            showAddGraphDialog = showAddGraphDialog,
            transactionFilter = transactionFilter,
            isRefreshing = isRefreshing
        )
    }
}
