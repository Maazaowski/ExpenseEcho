package com.expenseecho.data.repository

import com.expenseecho.data.dao.TransactionDao
import com.expenseecho.data.dao.RuleDao
import com.expenseecho.data.entity.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val ruleDao: RuleDao
) {
    
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAll()
    
    fun getTransactionsByAccount(accountId: String): Flow<List<Transaction>> = 
        transactionDao.getByAccount(accountId)
    
    fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>> = 
        transactionDao.getByCategory(categoryId)
    
    fun getTransactionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>> = 
        transactionDao.getByDateRange(startDate, endDate)
    
    fun getTransactionsByType(type: String): Flow<List<Transaction>> = 
        transactionDao.getByType(type)
    
    fun searchTransactions(query: String): Flow<List<Transaction>> = 
        transactionDao.searchTransactions(query)
    
    suspend fun getTransactionById(id: String): Transaction? = 
        transactionDao.getById(id)
    
    suspend fun getTotalIncome(startDate: LocalDate, endDate: LocalDate): Long = 
        transactionDao.getTotalIncome(startDate, endDate) ?: 0L
    
    suspend fun getTotalExpenses(startDate: LocalDate, endDate: LocalDate): Long = 
        transactionDao.getTotalExpenses(startDate, endDate) ?: 0L
    
    suspend fun getTotalByCategory(categoryId: String, startDate: LocalDate, endDate: LocalDate): Long = 
        transactionDao.getTotalByCategory(categoryId, startDate, endDate) ?: 0L
    
    suspend fun insertTransaction(transaction: Transaction) {
        val finalTransaction = if (transaction.categoryId == null) {
            // Auto-categorize using rules
            val categoryId = classifyCategory(transaction.merchant, transaction.description)
            transaction.copy(categoryId = categoryId)
        } else {
            transaction
        }
        
        transactionDao.insert(finalTransaction)
    }
    
    suspend fun insertTransactionWithId(transaction: Transaction): String {
        val id = UUID.randomUUID().toString()
        val transactionWithId = transaction.copy(id = id)
        insertTransaction(transactionWithId)
        return id
    }
    
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction.copy(updatedAt = System.currentTimeMillis()))
    }
    
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
    
    suspend fun deleteTransactionById(id: String) {
        transactionDao.deleteById(id)
    }
    
    private suspend fun classifyCategory(merchant: String?, description: String?): String? {
        val searchText = listOfNotNull(merchant, description)
            .joinToString(" ")
            .lowercase()
        
        if (searchText.isBlank()) return null
        
        val rules = ruleDao.getActiveRulesForMatching()
        return rules.firstOrNull { rule ->
            searchText.contains(rule.keyword.lowercase())
        }?.categoryId
    }
}
