package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {
    
    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC")
    fun getAll(): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    fun getByAccount(accountId: String): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId ORDER BY date DESC")
    fun getByCategory(categoryId: String): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getByType(type: String): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE merchant LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%' ORDER BY date DESC")
    fun searchTransactions(searchQuery: String): Flow<List<Transaction>>
    
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: String): Transaction?
    
    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Income' AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalIncome(startDate: LocalDate, endDate: LocalDate): Long?
    
    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Expense' AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalExpenses(startDate: LocalDate, endDate: LocalDate): Long?
    
    @Query("SELECT SUM(amount) FROM transactions WHERE categoryId = :categoryId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalByCategory(categoryId: String, startDate: LocalDate, endDate: LocalDate): Long?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transaction>)
    
    @Update
    suspend fun update(transaction: Transaction)
    
    @Delete
    suspend fun delete(transaction: Transaction)
    
    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: String)
}
