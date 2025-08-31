package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Budget
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

@Dao
interface BudgetDao {
    
    @Query("SELECT * FROM budgets ORDER BY month DESC, categoryId ASC")
    fun getAll(): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE month = :month ORDER BY categoryId ASC")
    fun getByMonth(month: YearMonth): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId ORDER BY month DESC")
    fun getByCategory(categoryId: String): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getById(id: String): Budget?
    
    @Query("SELECT * FROM budgets WHERE month = :month AND categoryId = :categoryId")
    suspend fun getByMonthAndCategory(month: YearMonth, categoryId: String): Budget?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(budgets: List<Budget>)
    
    @Update
    suspend fun update(budget: Budget)
    
    @Delete
    suspend fun delete(budget: Budget)
    
    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM budgets WHERE month = :month")
    suspend fun deleteByMonth(month: YearMonth)
}
