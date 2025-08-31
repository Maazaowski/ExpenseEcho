package com.expenseecho.data.repository

import com.expenseecho.data.dao.DebtDao
import com.expenseecho.data.entity.Debt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebtRepository @Inject constructor(
    private val debtDao: DebtDao
) {
    
    fun getAllActiveDebts(): Flow<List<Debt>> = debtDao.getAllActive()
    
    fun getAllDebts(): Flow<List<Debt>> = debtDao.getAll()
    
    suspend fun getDebtById(id: String): Debt? = debtDao.getById(id)
    
    suspend fun getTotalDebt(): Long = debtDao.getTotalDebt() ?: 0L
    
    suspend fun getTotalMinPayments(): Long = debtDao.getTotalMinPayments() ?: 0L
    
    suspend fun insertDebt(debt: Debt) {
        debtDao.insert(debt)
    }
    
    suspend fun updateDebt(debt: Debt) {
        debtDao.update(debt.copy(updatedAt = System.currentTimeMillis()))
    }
    
    suspend fun deleteDebt(debt: Debt) {
        debtDao.delete(debt)
    }
    
    suspend fun deactivateDebt(id: String) {
        debtDao.deactivate(id)
    }
    
    suspend fun updateDebtBalance(id: String, newBalance: Long) {
        debtDao.updateBalance(id, newBalance)
    }
    
    /**
     * Calculate debt avalanche order (highest APR first)
     */
    suspend fun getDebtAvalancheOrder(): List<Debt> {
        return debtDao.getAllActive().first().sortedByDescending { it.apr }
    }
    
    /**
     * Calculate months to payoff for a given extra payment amount
     */
    fun calculateMonthsToPayoff(debt: Debt, extraPayment: Long): Int {
        val balance = debt.balance.toDouble() / 100.0 // Convert to PKR
        val monthlyRate = debt.apr / 12.0 / 100.0
        val payment = (debt.minPayment + extraPayment).toDouble() / 100.0
        
        if (payment <= balance * monthlyRate) {
            return Int.MAX_VALUE // Payment too low, will never pay off
        }
        
        val months = -(kotlin.math.ln(1 - (balance * monthlyRate / payment)) / kotlin.math.ln(1 + monthlyRate))
        return kotlin.math.ceil(months).toInt()
    }
}
