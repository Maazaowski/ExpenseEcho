package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Merchant
import kotlinx.coroutines.flow.Flow

@Dao
interface MerchantDao {
    
    @Query("SELECT * FROM merchants ORDER BY transactionCount DESC, name ASC")
    fun getAll(): Flow<List<Merchant>>
    
    @Query("SELECT * FROM merchants WHERE categoryId IS NULL ORDER BY transactionCount DESC, name ASC")
    fun getUncategorized(): Flow<List<Merchant>>
    
    @Query("SELECT * FROM merchants WHERE categoryId = :categoryId ORDER BY transactionCount DESC, name ASC")
    fun getByCategory(categoryId: String): Flow<List<Merchant>>
    
    @Query("SELECT * FROM merchants WHERE id = :id")
    suspend fun getById(id: String): Merchant?
    
    @Query("SELECT * FROM merchants WHERE name = :name")
    suspend fun getByName(name: String): Merchant?
    
    @Query("SELECT * FROM merchants WHERE normalizedName = :normalizedName")
    suspend fun getByNormalizedName(normalizedName: String): Merchant?
    
    @Query("SELECT * FROM merchants WHERE normalizedName LIKE '%' || :searchTerm || '%' ORDER BY transactionCount DESC")
    suspend fun searchByNormalizedName(searchTerm: String): List<Merchant>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(merchant: Merchant)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(merchants: List<Merchant>)
    
    @Update
    suspend fun update(merchant: Merchant)
    
    @Delete
    suspend fun delete(merchant: Merchant)
    
    @Query("DELETE FROM merchants WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM merchants")
    suspend fun deleteAll()
    
    @Query("UPDATE merchants SET categoryId = :categoryId, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateCategory(id: String, categoryId: String?, updatedAt: Long)
    
    @Query("UPDATE merchants SET transactionCount = transactionCount + 1, lastSeenAt = :lastSeenAt WHERE id = :id")
    suspend fun incrementTransactionCount(id: String, lastSeenAt: Long)
    
    @Query("SELECT COUNT(*) FROM merchants WHERE categoryId IS NULL")
    suspend fun getUncategorizedCount(): Int
    
    @Query("SELECT COUNT(*) FROM merchants")
    suspend fun getTotalCount(): Int
}
