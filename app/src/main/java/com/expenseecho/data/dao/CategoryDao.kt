package com.expenseecho.data.dao

import androidx.room.*
import com.expenseecho.data.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAll(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE isBudgetable = 1 ORDER BY name ASC")
    fun getBudgetableCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): Category?
    
    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getByName(name: String): Category?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
    
    @Update
    suspend fun update(category: Category)
    
    @Delete
    suspend fun delete(category: Category)
    
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteById(id: String)
}
