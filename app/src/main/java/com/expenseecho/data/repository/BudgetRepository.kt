package com.expenseecho.data.repository

import com.expenseecho.data.dao.BudgetDao
import com.expenseecho.data.entity.Budget
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {
    
    fun getAllBudgets(): Flow<List<Budget>> = budgetDao.getAll()
    
    fun getBudgetsByMonth(month: YearMonth): Flow<List<Budget>> = budgetDao.getByMonth(month)
    
    fun getBudgetsByCategory(categoryId: String): Flow<List<Budget>> = budgetDao.getByCategory(categoryId)
    
    suspend fun getBudgetById(id: String): Budget? = budgetDao.getById(id)
    
    suspend fun getBudgetByMonthAndCategory(month: YearMonth, categoryId: String): Budget? = 
        budgetDao.getByMonthAndCategory(month, categoryId)
    
    suspend fun insertBudget(budget: Budget) {
        budgetDao.insert(budget)
    }
    
    suspend fun insertOrUpdateBudget(month: YearMonth, categoryId: String, planned: Long) {
        val id = "${month}:${categoryId}"
        val existing = getBudgetByMonthAndCategory(month, categoryId)
        
        if (existing != null) {
            val updated = existing.copy(
                planned = planned,
                updatedAt = System.currentTimeMillis()
            )
            budgetDao.update(updated)
        } else {
            val newBudget = Budget(
                id = id,
                month = month,
                categoryId = categoryId,
                planned = planned
            )
            budgetDao.insert(newBudget)
        }
    }
    
    suspend fun updateBudget(budget: Budget) {
        budgetDao.update(budget.copy(updatedAt = System.currentTimeMillis()))
    }
    
    suspend fun deleteBudget(budget: Budget) {
        budgetDao.delete(budget)
    }
    
    suspend fun deleteBudgetById(id: String) {
        budgetDao.deleteById(id)
    }
    
    suspend fun deleteBudgetsByMonth(month: YearMonth) {
        budgetDao.deleteByMonth(month)
    }
}
