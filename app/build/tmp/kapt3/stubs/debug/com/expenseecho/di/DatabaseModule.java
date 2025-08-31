package com.expenseecho.di;

import android.content.Context;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import com.expenseecho.data.database.ExpenseEchoDatabase;
import com.expenseecho.data.dao.*;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0012\u0010\u0010\u001a\u00020\u000b2\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\u0017"}, d2 = {"Lcom/expenseecho/di/DatabaseModule;", "", "()V", "generateRandomPassphrase", "", "getDatabasePassphrase", "context", "Landroid/content/Context;", "provideAccountDao", "Lcom/expenseecho/data/dao/AccountDao;", "database", "Lcom/expenseecho/data/database/ExpenseEchoDatabase;", "provideBudgetDao", "Lcom/expenseecho/data/dao/BudgetDao;", "provideCategoryDao", "Lcom/expenseecho/data/dao/CategoryDao;", "provideDatabase", "provideDebtDao", "Lcom/expenseecho/data/dao/DebtDao;", "provideRuleDao", "Lcom/expenseecho/data/dao/RuleDao;", "provideTransactionDao", "Lcom/expenseecho/data/dao/TransactionDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.expenseecho.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.database.ExpenseEchoDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.AccountDao provideAccountDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.TransactionDao provideTransactionDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.CategoryDao provideCategoryDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.BudgetDao provideBudgetDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.DebtDao provideDebtDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.dao.RuleDao provideRuleDao(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.database.ExpenseEchoDatabase database) {
        return null;
    }
    
    private final java.lang.String getDatabasePassphrase(android.content.Context context) {
        return null;
    }
    
    private final java.lang.String generateRandomPassphrase() {
        return null;
    }
}