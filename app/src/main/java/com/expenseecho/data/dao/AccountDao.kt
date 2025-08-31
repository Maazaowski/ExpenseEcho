package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    
    @Query("SELECT * FROM accounts WHERE isActive = 1 ORDER BY displayName ASC")
    fun getAllActive(): Flow<List<Account>>
    
    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: String): Account?
    
    @Query("SELECT * FROM accounts WHERE mask = :mask")
    suspend fun getByMask(mask: String): Account?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<Account>)
    
    @Update
    suspend fun update(account: Account)
    
    @Delete
    suspend fun delete(account: Account)
    
    @Query("UPDATE accounts SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: String)
}
