package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.data.entity.Transaction
import com.expenseecho.data.entity.Category
import com.expenseecho.data.entity.Account
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.CategoryRepository
import com.expenseecho.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransactionDetailsUiState(
    val transaction: Transaction? = null,
    val account: Account? = null,
    val category: Category? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    
    private val _transactionId = MutableStateFlow<String?>(null)
    
    private val _uiState = MutableStateFlow(TransactionDetailsUiState(isLoading = true))
    val uiState: StateFlow<TransactionDetailsUiState> = _uiState.asStateFlow()
    
    fun loadTransaction(transactionId: String) {
        _transactionId.value = transactionId
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load transaction
                val transaction = transactionRepository.getTransactionById(transactionId)
                
                if (transaction == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Transaction not found"
                    )
                    return@launch
                }
                
                // Load related data
                val account = accountRepository.getAccountById(transaction.accountId)
                val category = transaction.categoryId?.let { categoryId ->
                    categoryRepository.getCategoryById(categoryId)
                }
                val categories = categoryRepository.getAllCategories().first()
                
                _uiState.value = TransactionDetailsUiState(
                    transaction = transaction,
                    account = account,
                    category = category,
                    categories = categories,
                    isLoading = false,
                    error = null
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load transaction: ${e.message}"
                )
            }
        }
    }
    
    fun updateCategory(categoryId: String?) {
        val currentTransaction = _uiState.value.transaction ?: return
        
        viewModelScope.launch {
            try {
                val updatedTransaction = currentTransaction.copy(
                    categoryId = categoryId,
                    updatedAt = System.currentTimeMillis()
                )
                
                transactionRepository.updateTransaction(updatedTransaction)
                
                // Update UI state
                val newCategory = categoryId?.let { id ->
                    _uiState.value.categories.find { it.id == id }
                }
                
                _uiState.value = _uiState.value.copy(
                    transaction = updatedTransaction,
                    category = newCategory
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update category: ${e.message}"
                )
            }
        }
    }
}
