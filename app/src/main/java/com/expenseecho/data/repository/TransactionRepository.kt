package com.expenseecho.data.repository

import com.expenseecho.data.dao.TransactionDao
import com.expenseecho.data.dao.RuleDao
import com.expenseecho.data.entity.Transaction
import com.expenseecho.data.entity.Account
import com.expenseecho.data.database.ExpenseEchoDatabase
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val ruleDao: RuleDao,
    private val merchantRepository: MerchantRepository,
    private val database: ExpenseEchoDatabase,
    private val accountRepository: AccountRepository
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
    
    suspend fun getTransactionCount(): Int = transactionDao.getCount()
    
    
    suspend fun insertTransaction(transaction: Transaction) {
        android.util.Log.d("TransactionRepository", "Inserting transaction: ${transaction.id} - ${transaction.description}")
        
        // Check for duplicate transactions (only if reference exists)
        if (transaction.reference != null) {
            val existingTransaction = transactionDao.findDuplicate(
                transaction.reference, 
                transaction.date, 
                transaction.amount
            )
            
            if (existingTransaction != null) {
                android.util.Log.d("TransactionRepository", "🔄 Duplicate transaction found with reference ${transaction.reference} (existing: ${existingTransaction.id}), skipping insert")
                return
            }
        } else {
            android.util.Log.d("TransactionRepository", "No reference number, proceeding with insert")
        }
        
        val finalTransaction = if (transaction.categoryId == null && transaction.merchant != null) {
            // First, find or create merchant and get its category mapping
            val (merchantId, merchantCategoryId) = merchantRepository.findOrCreateMerchant(transaction.merchant)
            android.util.Log.d("TransactionRepository", "Merchant created/found: $merchantId, category: $merchantCategoryId")
            
            // Use merchant category if available, otherwise try rules-based categorization
            val categoryId = merchantCategoryId ?: classifyCategory(transaction.merchant, transaction.description)
            
            transaction.copy(categoryId = categoryId)
        } else if (transaction.categoryId == null) {
            // Auto-categorize using rules only
            val categoryId = classifyCategory(transaction.merchant, transaction.description)
            transaction.copy(categoryId = categoryId)
        } else {
            transaction
        }
        
        android.util.Log.d("TransactionRepository", "Final transaction before insert: ID=${finalTransaction.id}, Date=${finalTransaction.date}, Amount=${finalTransaction.amount}")
        
        try {
            // Log all the foreign key values before insert
            android.util.Log.d("TransactionRepository", "Attempting to insert transaction with:")
            android.util.Log.d("TransactionRepository", "  - AccountId: ${finalTransaction.accountId}")
            android.util.Log.d("TransactionRepository", "  - CategoryId: ${finalTransaction.categoryId}")
            
            transactionDao.insert(finalTransaction)
            android.util.Log.d("TransactionRepository", "Transaction inserted successfully")
            
            // If merchant exists, increment its transaction count
            if (finalTransaction.merchant != null) {
                val merchant = merchantRepository.findMerchantForCategorization(finalTransaction.merchant)
                merchant?.let { 
                    merchantRepository.incrementTransactionCount(it.id)
                    android.util.Log.d("TransactionRepository", "Incremented transaction count for merchant: ${it.name}")
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("TransactionRepository", "Error inserting transaction: ${e.message}", e)
            android.util.Log.e("TransactionRepository", "Transaction details - AccountId: ${finalTransaction.accountId}, CategoryId: ${finalTransaction.categoryId}")
            throw e
        }
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
    
    /**
     * Atomically insert transaction with account creation if needed.
     * This ensures both operations happen in a single database transaction.
     */
    suspend fun insertTransactionWithAccount(
        transaction: Transaction,
        accountMask: String?
    ) {
        database.withTransaction {
            android.util.Log.d("TransactionRepository", "🔄 Starting atomic transaction: ${transaction.id}")
            
            // Ensure account exists
            if (accountMask != null) {
                val existingAccount = accountRepository.getAccountByMask(accountMask)
                if (existingAccount == null) {
                    // Check if account already exists by ID to prevent REPLACE conflicts
                    val existingAccountById = accountRepository.getAccountById("spend-0030-1")
                    if (existingAccountById == null) {
                        val defaultAccount = Account(
                            id = "spend-0030-1",
                            displayName = "Spending (6809)",
                            mask = accountMask
                        )
                        accountRepository.insertAccount(defaultAccount)
                        android.util.Log.d("TransactionRepository", "✅ Created account in transaction: spend-0030-1")
                    } else {
                        // Account exists with this ID, just update the mask if needed
                        if (existingAccountById.mask != accountMask) {
                            val updatedAccount = existingAccountById.copy(mask = accountMask)
                            accountRepository.updateAccount(updatedAccount)
                            android.util.Log.d("TransactionRepository", "✅ Updated account mask in transaction: ${existingAccountById.id}")
                        } else {
                            android.util.Log.d("TransactionRepository", "✅ Using existing account in transaction: ${existingAccountById.id}")
                        }
                    }
                }
            }
            
            // Process the transaction (merchant, categorization) and insert
            // Check for duplicate transactions (only if reference exists)
            if (transaction.reference != null) {
                val existingTransaction = transactionDao.findDuplicate(
                    transaction.reference, 
                    transaction.date, 
                    transaction.amount
                )
                
                if (existingTransaction != null) {
                    android.util.Log.d("TransactionRepository", "🔄 Duplicate transaction found with reference ${transaction.reference} (existing: ${existingTransaction.id}), skipping insert")
                    return@withTransaction
                }
            }
            
            val finalTransaction = if (transaction.categoryId == null && transaction.merchant != null) {
                val (merchantId, merchantCategoryId) = merchantRepository.findOrCreateMerchant(transaction.merchant)
                android.util.Log.d("TransactionRepository", "Merchant created/found: $merchantId, category: $merchantCategoryId")
                val categoryId = merchantCategoryId ?: classifyCategory(transaction.merchant, transaction.description)
                transaction.copy(categoryId = categoryId)
            } else if (transaction.categoryId == null) {
                val categoryId = classifyCategory(transaction.merchant, transaction.description)
                transaction.copy(categoryId = categoryId)
            } else {
                transaction
            }

            // Insert the final transaction
            transactionDao.insert(finalTransaction)
            
            // Update merchant transaction count if applicable
            if (finalTransaction.merchant != null) {
                val merchant = merchantRepository.findMerchantForCategorization(finalTransaction.merchant)
                merchant?.let {
                    merchantRepository.incrementTransactionCount(it.id)
                }
            }
            
            android.util.Log.d("TransactionRepository", "✅ Transaction inserted: ${transaction.id}")
        }
    }
}
