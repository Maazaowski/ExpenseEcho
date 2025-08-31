package com.expenseecho.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import com.expenseecho.data.converter.DateConverters;
import com.expenseecho.data.dao.*;
import com.expenseecho.data.entity.*;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u0010"}, d2 = {"Lcom/expenseecho/data/database/ExpenseEchoDatabase;", "Landroidx/room/RoomDatabase;", "()V", "accountDao", "Lcom/expenseecho/data/dao/AccountDao;", "budgetDao", "Lcom/expenseecho/data/dao/BudgetDao;", "categoryDao", "Lcom/expenseecho/data/dao/CategoryDao;", "debtDao", "Lcom/expenseecho/data/dao/DebtDao;", "ruleDao", "Lcom/expenseecho/data/dao/RuleDao;", "transactionDao", "Lcom/expenseecho/data/dao/TransactionDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.expenseecho.data.entity.Account.class, com.expenseecho.data.entity.Transaction.class, com.expenseecho.data.entity.Category.class, com.expenseecho.data.entity.Budget.class, com.expenseecho.data.entity.Debt.class, com.expenseecho.data.entity.Rule.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.expenseecho.data.converter.DateConverters.class})
public abstract class ExpenseEchoDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.expenseecho.data.database.ExpenseEchoDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.expenseecho.data.database.ExpenseEchoDatabase.Companion Companion = null;
    
    public ExpenseEchoDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.AccountDao accountDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.TransactionDao transactionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.BudgetDao budgetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.DebtDao debtDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.expenseecho.data.dao.RuleDao ruleDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/expenseecho/data/database/ExpenseEchoDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/expenseecho/data/database/ExpenseEchoDatabase;", "getDatabase", "context", "Landroid/content/Context;", "passphrase", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.expenseecho.data.database.ExpenseEchoDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String passphrase) {
            return null;
        }
    }
}