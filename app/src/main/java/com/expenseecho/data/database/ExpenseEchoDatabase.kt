package com.expenseecho.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.expenseecho.data.converter.DateConverters
import com.expenseecho.data.dao.*
import com.expenseecho.data.entity.*
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        Account::class,
        Transaction::class,
        Category::class,
        Budget::class,
        Debt::class,
        Rule::class,
        Merchant::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class ExpenseEchoDatabase : RoomDatabase() {
    
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun debtDao(): DebtDao
    abstract fun ruleDao(): RuleDao
    abstract fun merchantDao(): MerchantDao
    
    companion object {
        @Volatile
        private var INSTANCE: ExpenseEchoDatabase? = null
        
        fun getDatabase(context: Context, passphrase: String): ExpenseEchoDatabase {
            return INSTANCE ?: synchronized(this) {
                val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()))
                
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseEchoDatabase::class.java,
                    "expense_echo_database"
                )
                    .openHelperFactory(factory)
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration() // For development - removes this for production
                    .build()
                
                INSTANCE = instance
                DatabaseCallback.setInstance(instance)
                instance
            }
        }
    }
}
