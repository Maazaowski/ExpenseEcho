package com.expenseecho.data.repository

import com.expenseecho.data.dao.AccountDao
import com.expenseecho.data.entity.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) {
    
    fun getAllActiveAccounts(): Flow<List<Account>> = accountDao.getAllActive()
    
    suspend fun getAccountById(id: String): Account? = accountDao.getById(id)
    
    suspend fun getAccountByMask(mask: String): Account? = accountDao.getByMask(mask)
    
    suspend fun insertAccount(account: Account) {
        accountDao.insert(account)
    }
    
    suspend fun updateAccount(account: Account) {
        accountDao.update(account)
    }
    
    suspend fun deleteAccount(account: Account) {
        accountDao.delete(account)
    }
    
    suspend fun deactivateAccount(id: String) {
        accountDao.deactivate(id)
    }
}
