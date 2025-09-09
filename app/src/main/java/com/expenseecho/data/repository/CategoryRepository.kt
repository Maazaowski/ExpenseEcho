package com.expenseecho.data.repository

import com.expenseecho.data.dao.CategoryDao
import com.expenseecho.data.entity.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    
    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAll()
    
    fun getBudgetableCategories(): Flow<List<Category>> = categoryDao.getBudgetableCategories()
    
    suspend fun getCategoryById(id: String): Category? = categoryDao.getById(id)
    
    suspend fun getCategoryByName(name: String): Category? = categoryDao.getByName(name)
    
    suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }
    
    suspend fun updateCategory(category: Category) {
        categoryDao.update(category)
    }
    
    suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }
    
    suspend fun deleteCategoryById(id: String) {
        categoryDao.deleteById(id)
    }
    
    suspend fun createCustomCategory(name: String): Category {
        val category = Category(
            id = name.lowercase().replace(" ", "_"),
            name = name,
            color = "#9E9E9E", // Default gray color
            icon = "category"
        )
        categoryDao.insert(category)
        return category
    }
}
