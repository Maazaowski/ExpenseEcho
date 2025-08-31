package com.expenseecho.data.database;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.expenseecho.data.entity.Account;
import com.expenseecho.data.entity.Category;
import com.expenseecho.data.entity.Rule;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/expenseecho/data/database/DatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "()V", "applicationScope", "Lkotlinx/coroutines/CoroutineScope;", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "populateDatabase", "database", "Lcom/expenseecho/data/database/ExpenseEchoDatabase;", "(Lcom/expenseecho/data/database/ExpenseEchoDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class DatabaseCallback extends androidx.room.RoomDatabase.Callback {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope applicationScope = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.expenseecho.data.database.ExpenseEchoDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.expenseecho.data.database.DatabaseCallback.Companion Companion = null;
    
    public DatabaseCallback() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.NotNull()
    androidx.sqlite.db.SupportSQLiteDatabase db) {
    }
    
    private final java.lang.Object populateDatabase(com.expenseecho.data.database.ExpenseEchoDatabase database, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/expenseecho/data/database/DatabaseCallback$Companion;", "", "()V", "INSTANCE", "Lcom/expenseecho/data/database/ExpenseEchoDatabase;", "setInstance", "", "instance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void setInstance(@org.jetbrains.annotations.NotNull()
        com.expenseecho.data.database.ExpenseEchoDatabase instance) {
        }
    }
}