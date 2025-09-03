package com.expenseecho.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        
        // Migration from version 1 to 2 - adds Merchant table
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create merchants table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `merchants` (
                        `id` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `normalizedName` TEXT NOT NULL,
                        `categoryId` TEXT,
                        `transactionCount` INTEGER NOT NULL DEFAULT 0,
                        `lastSeenAt` INTEGER NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )
                """.trimIndent())
                
                // Create indices for merchants table
                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_merchants_name` ON `merchants` (`name`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_merchants_categoryId` ON `merchants` (`categoryId`)")
            }
        }
        
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
                    .addMigrations(MIGRATION_1_2)
                    .build()
                
                INSTANCE = instance
                DatabaseCallback.setInstance(instance)
                instance
            }
        }
    }
}
