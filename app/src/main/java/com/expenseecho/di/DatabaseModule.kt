package com.expenseecho.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.expenseecho.data.database.ExpenseEchoDatabase
import com.expenseecho.data.dao.*
import com.expenseecho.data.analytics.AnalyticsEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExpenseEchoDatabase {
        // Get or create database passphrase
        val passphrase = getDatabasePassphrase(context)
        return ExpenseEchoDatabase.getDatabase(context, passphrase)
    }
    
    @Provides
    fun provideAccountDao(database: ExpenseEchoDatabase): AccountDao = database.accountDao()
    
    @Provides
    fun provideTransactionDao(database: ExpenseEchoDatabase): TransactionDao = database.transactionDao()
    
    @Provides
    fun provideCategoryDao(database: ExpenseEchoDatabase): CategoryDao = database.categoryDao()
    
    @Provides
    fun provideBudgetDao(database: ExpenseEchoDatabase): BudgetDao = database.budgetDao()
    
    @Provides
    fun provideDebtDao(database: ExpenseEchoDatabase): DebtDao = database.debtDao()
    
    @Provides
    fun provideRuleDao(database: ExpenseEchoDatabase): RuleDao = database.ruleDao()
    
    @Provides
    fun provideMerchantDao(database: ExpenseEchoDatabase): MerchantDao = database.merchantDao()
    
    @Provides
    @Singleton
    fun provideAnalyticsEngine(
        transactionRepository: com.expenseecho.data.repository.TransactionRepository,
        categoryRepository: com.expenseecho.data.repository.CategoryRepository,
        budgetRepository: com.expenseecho.data.repository.BudgetRepository
    ): AnalyticsEngine = AnalyticsEngine(transactionRepository, categoryRepository, budgetRepository)
    
    private fun getDatabasePassphrase(context: Context): String {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        // Get existing passphrase or generate new one
        return sharedPreferences.getString("db_passphrase", null) ?: run {
            val newPassphrase = generateRandomPassphrase()
            sharedPreferences.edit()
                .putString("db_passphrase", newPassphrase)
                .apply()
            newPassphrase
        }
    }
    
    private fun generateRandomPassphrase(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*"
        return (1..32)
            .map { charset.random() }
            .joinToString("")
    }
}
