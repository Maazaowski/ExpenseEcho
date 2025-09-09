package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.data.entity.Merchant
import com.expenseecho.data.entity.Category
import com.expenseecho.data.repository.MerchantRepository
import com.expenseecho.data.repository.CategoryRepository
import com.expenseecho.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MerchantUiState(
    val merchants: List<Merchant> = emptyList(),
    val categories: List<Category> = emptyList(),
    val filteredMerchants: List<Merchant> = emptyList(),
    val selectedFilter: MerchantFilter = MerchantFilter.All,
    val searchQuery: String = "",
    val uncategorizedCount: Int = 0,
    val totalCount: Int = 0,
    val isLoading: Boolean = false
)

sealed class MerchantFilter {
    object All : MerchantFilter()
    object Uncategorized : MerchantFilter()
    data class Category(val categoryId: String, val categoryName: String) : MerchantFilter()
}

@HiltViewModel
class MerchantViewModel @Inject constructor(
    private val merchantRepository: MerchantRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow<MerchantFilter>(MerchantFilter.All)
    val selectedFilter: StateFlow<MerchantFilter> = _selectedFilter.asStateFlow()
    
    val uiState: StateFlow<MerchantUiState> = combine(
        merchantRepository.getAllMerchants(),
        categoryRepository.getAllCategories(),
        _searchQuery,
        _selectedFilter
    ) { merchants, categories, query, filter ->
        val filtered = filterMerchants(merchants, query, filter)
        
        MerchantUiState(
            merchants = merchants,
            categories = categories, // Show all categories for merchant mapping
            filteredMerchants = filtered,
            selectedFilter = filter,
            searchQuery = query,
            uncategorizedCount = merchants.count { it.categoryId == null },
            totalCount = merchants.size,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MerchantUiState(isLoading = true)
    )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun updateFilter(filter: MerchantFilter) {
        _selectedFilter.value = filter
    }
    
    fun updateMerchantCategory(merchantId: String, categoryId: String?) {
        viewModelScope.launch {
            // Update the merchant's category
            merchantRepository.updateMerchantCategory(merchantId, categoryId)
            
            // Get the merchant name and update all related transactions
            val merchantName = merchantRepository.getMerchantNameById(merchantId)
            if (merchantName != null) {
                transactionRepository.updateTransactionsByMerchant(merchantName, categoryId)
            }
        }
    }
    
    fun deleteMerchant(merchant: Merchant) {
        viewModelScope.launch {
            merchantRepository.deleteMerchant(merchant)
        }
    }
    
    fun createCustomCategory(name: String) {
        viewModelScope.launch {
            try {
                categoryRepository.createCustomCategory(name)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
    
    private fun filterMerchants(
        merchants: List<Merchant>,
        query: String,
        filter: MerchantFilter
    ): List<Merchant> {
        var filtered = merchants
        
        // Apply search filter
        if (query.isNotBlank()) {
            filtered = filtered.filter { merchant ->
                merchant.name.contains(query, ignoreCase = true) ||
                merchant.normalizedName.contains(query, ignoreCase = true)
            }
        }
        
        // Apply category filter
        filtered = when (filter) {
            is MerchantFilter.All -> filtered
            is MerchantFilter.Uncategorized -> filtered.filter { it.categoryId == null }
            is MerchantFilter.Category -> filtered.filter { it.categoryId == filter.categoryId }
        }
        
        // Sort by transaction count (most used first), then by name
        return filtered.sortedWith(
            compareByDescending<Merchant> { it.transactionCount }
                .thenBy { it.name }
        )
    }
}
