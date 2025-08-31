package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Debt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {
    
    @Query("SELECT * FROM debts WHERE isActive = 1 ORDER BY apr DESC, balance DESC")
    fun getAllActive(): Flow<List<Debt>>
    
    @Query("SELECT * FROM debts ORDER BY apr DESC, balance DESC")
    fun getAll(): Flow<List<Debt>>
    
    @Query("SELECT * FROM debts WHERE id = :id")
    suspend fun getById(id: String): Debt?
    
    @Query("SELECT SUM(balance) FROM debts WHERE isActive = 1")
    suspend fun getTotalDebt(): Long?
    
    @Query("SELECT SUM(minPayment) FROM debts WHERE isActive = 1")
    suspend fun getTotalMinPayments(): Long?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(debt: Debt)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(debts: List<Debt>)
    
    @Update
    suspend fun update(debt: Debt)
    
    @Delete
    suspend fun delete(debt: Debt)
    
    @Query("UPDATE debts SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: String)
    
    @Query("UPDATE debts SET balance = :newBalance, updatedAt = :timestamp WHERE id = :id")
    suspend fun updateBalance(id: String, newBalance: Long, timestamp: Long = System.currentTimeMillis())
}
