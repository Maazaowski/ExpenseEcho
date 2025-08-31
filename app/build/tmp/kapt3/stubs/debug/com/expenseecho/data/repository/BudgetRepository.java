package com.expenseecho.data.repository;

import com.expenseecho.data.dao.BudgetDao;
import com.expenseecho.data.entity.Budget;
import kotlinx.coroutines.flow.Flow;
import java.time.YearMonth;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ \u0010\u0016\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u00132\u0006\u0010\u0017\u001a\u00020\fJ\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u00132\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ&\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/expenseecho/data/repository/BudgetRepository;", "", "budgetDao", "Lcom/expenseecho/data/dao/BudgetDao;", "(Lcom/expenseecho/data/dao/BudgetDao;)V", "deleteBudget", "", "budget", "Lcom/expenseecho/data/entity/Budget;", "(Lcom/expenseecho/data/entity/Budget;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBudgetById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBudgetsByMonth", "month", "Ljava/time/YearMonth;", "(Ljava/time/YearMonth;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllBudgets", "Lkotlinx/coroutines/flow/Flow;", "", "getBudgetById", "getBudgetByMonthAndCategory", "categoryId", "(Ljava/time/YearMonth;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBudgetsByCategory", "getBudgetsByMonth", "insertBudget", "insertOrUpdateBudget", "planned", "", "(Ljava/time/YearMonth;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBudget", "app_debug"})
public final class BudgetRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.dao.BudgetDao budgetDao = null;
    
    @javax.inject.Inject()
    public BudgetRepository(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.dao.BudgetDao budgetDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.expenseecho.data.entity.Budget>> getAllBudgets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.expenseecho.data.entity.Budget>> getBudgetsByMonth(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth month) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.expenseecho.data.entity.Budget>> getBudgetsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getBudgetById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.expenseecho.data.entity.Budget> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getBudgetByMonthAndCategory(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth month, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.expenseecho.data.entity.Budget> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertBudget(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertOrUpdateBudget(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth month, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, long planned, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateBudget(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteBudget(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteBudgetById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteBudgetsByMonth(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth month, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}