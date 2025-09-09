package com.expenseecho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expenseecho.service.SmsReaderService
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isImporting: Boolean = false,
    val importType: String = "", // "recent" or "all"
    val importProgress: Float = 0f,
    val importMessage: String = "",
    val dbStats: String = ""
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val smsReaderService: SmsReaderService,
    private val transactionRepository: TransactionRepository,
    private val merchantRepository: MerchantRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    init {
        loadDatabaseStats()
    }
    
    fun onSmsPermissionGranted() {
        _uiState.value = _uiState.value.copy(
            importMessage = "SMS permission granted! You can now import transactions."
        )
        loadDatabaseStats()
    }
    
    private fun loadDatabaseStats() {
        viewModelScope.launch {
            try {
                val transactionCount = transactionRepository.getAllTransactions().first().size
                val merchantCount = merchantRepository.getTotalMerchantCount()
                _uiState.value = _uiState.value.copy(
                    dbStats = "DB: $transactionCount transactions, $merchantCount merchants"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    dbStats = "DB Stats error: ${e.message}"
                )
            }
        }
    }
    
    fun importRecentSms() {
        if (_uiState.value.isImporting) return
        
        _uiState.value = _uiState.value.copy(
            isImporting = true,
            importType = "recent",
            importProgress = 0f,
            importMessage = "Starting import of recent SMS..."
        )
        
        viewModelScope.launch {
            try {
                smsReaderService.importRecentBankSms(30) { importedCount ->
                    _uiState.value = _uiState.value.copy(
                        isImporting = false,
                        importProgress = 1f,
                        importMessage = if (importedCount > 0) 
                            "Successfully imported $importedCount transactions from recent SMS"
                        else 
                            "No new transactions found in recent SMS"
                    )
                    loadDatabaseStats()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    importMessage = "Error importing SMS: ${e.message}"
                )
            }
        }
    }
    
    fun importAllSms() {
        if (_uiState.value.isImporting) return
        
        _uiState.value = _uiState.value.copy(
            isImporting = true,
            importType = "all",
            importProgress = 0f,
            importMessage = "Starting import of all SMS..."
        )
        
        viewModelScope.launch {
            try {
                smsReaderService.importAllBankSms { importedCount ->
                    _uiState.value = _uiState.value.copy(
                        isImporting = false,
                        importProgress = 1f,
                        importMessage = if (importedCount > 0) 
                            "Successfully imported $importedCount transactions from all SMS"
                        else 
                            "No transactions found in SMS history"
                    )
                    loadDatabaseStats()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    importMessage = "Error importing SMS: ${e.message}"
                )
            }
        }
    }
    
    fun clearImportMessage() {
        _uiState.value = _uiState.value.copy(importMessage = "")
    }
    
    fun clearAllData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isImporting = true,
                    importMessage = "Clearing all data..."
                )
                
                // Delete all transactions and merchants
                transactionRepository.deleteAllTransactions()
                merchantRepository.deleteAllMerchants()
                
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    importMessage = "All data cleared successfully",
                    dbStats = "DB: 0 transactions, 0 merchants"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    importMessage = "Error clearing data: ${e.message}"
                )
            }
        }
    }
}
