package com.expenseecho.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.expenseecho.data.entity.Account
import com.expenseecho.data.entity.Category
import com.expenseecho.data.entity.Rule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DatabaseCallback : RoomDatabase.Callback() {
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        
        // Pre-populate database with default data
        INSTANCE?.let { database ->
            applicationScope.launch {
                populateDatabase(database)
            }
        }
    }
    
    private suspend fun populateDatabase(database: ExpenseEchoDatabase) {
        // Default account (the target account from SMS filtering)
        val defaultAccount = Account(
            id = "spend-0030-1",
            displayName = "Spending (6809)",
            mask = "****0030-1******6809"
        )
        database.accountDao().insert(defaultAccount)
        
        // Default categories
        val defaultCategories = listOf(
            Category(id = "food", name = "Food & Dining", color = "#FF5722", icon = "restaurant"),
            Category(id = "groceries", name = "Groceries", color = "#4CAF50", icon = "local_grocery_store"),
            Category(id = "transport", name = "Transportation", color = "#2196F3", icon = "directions_car"),
            Category(id = "bills", name = "Bills & Utilities", color = "#FF9800", icon = "receipt_long"),
            Category(id = "shopping", name = "Shopping", color = "#E91E63", icon = "shopping_bag"),
            Category(id = "entertainment", name = "Entertainment", color = "#9C27B0", icon = "movie"),
            Category(id = "health", name = "Healthcare", color = "#009688", icon = "local_hospital"),
            Category(id = "education", name = "Education", color = "#3F51B5", icon = "school"),
            Category(id = "fuel", name = "Fuel", color = "#795548", icon = "local_gas_station"),
            Category(id = "transfer", name = "Transfer", color = "#607D8B", icon = "swap_horiz", isBudgetable = false),
            Category(id = "other", name = "Other", color = "#9E9E9E", icon = "more_horiz")
        )
        database.categoryDao().insertAll(defaultCategories)
        
        // Default rules for auto-categorization (enhanced for Pakistani market)
        val defaultRules = listOf(
            // Food & Dining (Popular in Pakistan)
            Rule(id = "foodpanda", keyword = "food panda", categoryId = "food", priority = 15),
            Rule(id = "kfc", keyword = "kfc", categoryId = "food", priority = 10),
            Rule(id = "mcdonalds", keyword = "mcdonald", categoryId = "food", priority = 10),
            Rule(id = "pizza", keyword = "pizza", categoryId = "food", priority = 10),
            Rule(id = "subway", keyword = "subway", categoryId = "food", priority = 10),
            Rule(id = "restaurant", keyword = "restaurant", categoryId = "food", priority = 5),
            Rule(id = "cafe", keyword = "cafe", categoryId = "food", priority = 5),
            Rule(id = "hotel", keyword = "hotel", categoryId = "food", priority = 5),
            Rule(id = "food", keyword = "food", categoryId = "food", priority = 3),
            
            // Shopping (Pakistani platforms)
            Rule(id = "daraz", keyword = "daraz", categoryId = "shopping", priority = 15),
            Rule(id = "shophive", keyword = "shophive", categoryId = "shopping", priority = 12),
            Rule(id = "homeshopping", keyword = "homeshopping", categoryId = "shopping", priority = 12),
            Rule(id = "shopping", keyword = "shopping", categoryId = "shopping", priority = 5),
            
            // Groceries (Pakistani stores)
            Rule(id = "carrefour", keyword = "carrefour", categoryId = "groceries", priority = 12),
            Rule(id = "metro", keyword = "metro", categoryId = "groceries", priority = 12),
            Rule(id = "hyperstar", keyword = "hyperstar", categoryId = "groceries", priority = 12),
            Rule(id = "imtiaz", keyword = "imtiaz", categoryId = "groceries", priority = 12),
            Rule(id = "mart", keyword = "mart", categoryId = "groceries", priority = 8),
            Rule(id = "super", keyword = "super", categoryId = "groceries", priority = 5),
            Rule(id = "store", keyword = "store", categoryId = "groceries", priority = 3),
            
            // Transportation (Pakistani services)
            Rule(id = "uber", keyword = "uber", categoryId = "transport", priority = 12),
            Rule(id = "careem", keyword = "careem", categoryId = "transport", priority = 12),
            Rule(id = "bykea", keyword = "bykea", categoryId = "transport", priority = 12),
            Rule(id = "taxi", keyword = "taxi", categoryId = "transport", priority = 8),
            Rule(id = "transport", keyword = "transport", categoryId = "transport", priority = 5),
            
            // Fuel (Pakistani brands)
            Rule(id = "pso", keyword = "pso", categoryId = "fuel", priority = 12),
            Rule(id = "shell", keyword = "shell", categoryId = "fuel", priority = 12),
            Rule(id = "total", keyword = "total", categoryId = "fuel", priority = 12),
            Rule(id = "byco", keyword = "byco", categoryId = "fuel", priority = 12),
            Rule(id = "attock", keyword = "attock", categoryId = "fuel", priority = 12),
            Rule(id = "fuel", keyword = "fuel", categoryId = "fuel", priority = 8),
            Rule(id = "petrol", keyword = "petrol", categoryId = "fuel", priority = 8),
            Rule(id = "cng", keyword = "cng", categoryId = "fuel", priority = 8),
            
            // Bills & Utilities
            Rule(id = "kelectric", keyword = "k-electric", categoryId = "bills", priority = 12),
            Rule(id = "lesco", keyword = "lesco", categoryId = "bills", priority = 12),
            Rule(id = "iesco", keyword = "iesco", categoryId = "bills", priority = 12),
            Rule(id = "ssgc", keyword = "ssgc", categoryId = "bills", priority = 12),
            Rule(id = "sngpl", keyword = "sngpl", categoryId = "bills", priority = 12),
            Rule(id = "ptcl", keyword = "ptcl", categoryId = "bills", priority = 12),
            Rule(id = "electric", keyword = "electric", categoryId = "bills", priority = 8),
            Rule(id = "gas", keyword = "gas", categoryId = "bills", priority = 8),
            Rule(id = "water", keyword = "water", categoryId = "bills", priority = 8),
            Rule(id = "bill", keyword = "bill", categoryId = "bills", priority = 5),
            
            // Healthcare
            Rule(id = "hospital", keyword = "hospital", categoryId = "health", priority = 10),
            Rule(id = "pharmacy", keyword = "pharmacy", categoryId = "health", priority = 10),
            Rule(id = "clinic", keyword = "clinic", categoryId = "health", priority = 8),
            Rule(id = "medical", keyword = "medical", categoryId = "health", priority = 8),
            
            // Transfer/Person-to-Person
            Rule(id = "transfer", keyword = "transfer", categoryId = "transfer", priority = 10),
            Rule(id = "funds", keyword = "funds", categoryId = "transfer", priority = 10),
            Rule(id = "trsfr", keyword = "trsfr", categoryId = "transfer", priority = 10)
        )
        database.ruleDao().insertAll(defaultRules)
    }
    
    companion object {
        @Volatile
        private var INSTANCE: ExpenseEchoDatabase? = null
        
        fun setInstance(instance: ExpenseEchoDatabase) {
            INSTANCE = instance
        }
    }
}
