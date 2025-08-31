package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.CategoryRepository
import com.expenseecho.data.repository.BudgetRepository
import com.expenseecho.data.entity.Category
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
    val isLoading: Boolean = false
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
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()
    
    val uiState: StateFlow<DashboardUiState> = combine(
        _currentMonth,
        categoryRepository.getAllCategories()
    ) { month, categories ->
        calculateDashboardData(month, categories)
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
    
    private suspend fun calculateDashboardData(
        month: YearMonth,
        categories: List<Category>
    ): DashboardUiState {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        val totalIncome = transactionRepository.getTotalIncome(startDate, endDate)
        val totalExpenses = transactionRepository.getTotalExpenses(startDate, endDate)
        val netAmount = totalIncome - totalExpenses
        
        val savingsRate = if (totalIncome > 0) {
            (netAmount.toFloat() / totalIncome.toFloat()) * 100f
        } else 0f
        
        // Calculate category spending
        val categorySpending = mutableListOf<CategorySpending>()
        for (category in categories.filter { it.isBudgetable }) {
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
        
        // Sort by amount descending
        categorySpending.sortByDescending { it.amount }
        
        return DashboardUiState(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            netAmount = netAmount,
            savingsRate = savingsRate,
            categorySpending = categorySpending,
            currentMonth = month,
            isLoading = false
        )
    }
}
