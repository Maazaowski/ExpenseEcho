package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.data.entity.Transaction
import com.expenseecho.data.entity.Category
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class TransactionUiState(
    val transactions: List<Transaction> = emptyList(),
    val categories: List<Category> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val selectedFilter: TransactionFilter = TransactionFilter.All,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

sealed class TransactionFilter {
    object All : TransactionFilter()
    object Income : TransactionFilter()
    object Expense : TransactionFilter() // Now includes transfers as a subcategory
    data class Category(val categoryId: String) : TransactionFilter()
    data class DateRange(val startDate: LocalDate, val endDate: LocalDate) : TransactionFilter()
}

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow<TransactionFilter>(TransactionFilter.All)
    val selectedFilter: StateFlow<TransactionFilter> = _selectedFilter.asStateFlow()
    
    val uiState: StateFlow<TransactionUiState> = combine(
        transactionRepository.getAllTransactions(),
        categoryRepository.getAllCategories(),
        _searchQuery,
        _selectedFilter
    ) { transactions, categories, query, filter ->
        val filtered = filterTransactions(transactions, query, filter)
        
        // Log only significant changes
        if (transactions.size % 10 == 0 || transactions.size < 10) {
            android.util.Log.d("TransactionViewModel", "UI updated: ${transactions.size} total, ${filtered.size} filtered")
        }
        
        TransactionUiState(
            transactions = transactions,
            categories = categories,
            filteredTransactions = filtered,
            selectedFilter = filter,
            searchQuery = query,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TransactionUiState(isLoading = true)
    )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun updateFilter(filter: TransactionFilter) {
        _selectedFilter.value = filter
    }
    
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transaction)
        }
    }
    
    private fun filterTransactions(
        transactions: List<Transaction>,
        query: String,
        filter: TransactionFilter
    ): List<Transaction> {
        var filtered = transactions
        
        // Apply search query
        if (query.isNotBlank()) {
            filtered = filtered.filter { transaction ->
                transaction.description?.contains(query, ignoreCase = true) == true ||
                transaction.merchant?.contains(query, ignoreCase = true) == true ||
                transaction.reference?.contains(query, ignoreCase = true) == true
            }
        }
        
        // Apply filter
        filtered = when (filter) {
            is TransactionFilter.All -> filtered
            is TransactionFilter.Income -> filtered.filter { it.type == "Income" }
            is TransactionFilter.Expense -> filtered.filter { it.type == "Expense" } // Now includes transfers
            is TransactionFilter.Category -> filtered.filter { it.categoryId == filter.categoryId }
            is TransactionFilter.DateRange -> filtered.filter { 
                it.date >= filter.startDate && it.date <= filter.endDate 
            }
        }
        
        return filtered.sortedByDescending { it.date }
    }
}
