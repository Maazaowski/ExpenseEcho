package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Rule
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {
    
    @Query("SELECT * FROM rules WHERE isActive = 1 ORDER BY priority DESC, keyword ASC")
    fun getAllActive(): Flow<List<Rule>>
    
    @Query("SELECT * FROM rules ORDER BY priority DESC, keyword ASC")
    fun getAll(): Flow<List<Rule>>
    
    @Query("SELECT * FROM rules WHERE isActive = 1 ORDER BY priority DESC")
    suspend fun getActiveRulesForMatching(): List<Rule>
    
    @Query("SELECT * FROM rules WHERE categoryId = :categoryId ORDER BY priority DESC")
    fun getByCategory(categoryId: String): Flow<List<Rule>>
    
    @Query("SELECT * FROM rules WHERE id = :id")
    suspend fun getById(id: String): Rule?
    
    @Query("SELECT * FROM rules WHERE keyword = :keyword")
    suspend fun getByKeyword(keyword: String): Rule?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: Rule)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rules: List<Rule>)
    
    @Update
    suspend fun update(rule: Rule)
    
    @Delete
    suspend fun delete(rule: Rule)
    
    @Query("DELETE FROM rules WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("UPDATE rules SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: String)
}
