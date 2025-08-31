package com.expenseecho.data.repository;

import com.expenseecho.data.dao.DebtDao;
import com.expenseecho.data.entity.Debt;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013J\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0019\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u001a\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u001d\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/expenseecho/data/repository/DebtRepository;", "", "debtDao", "Lcom/expenseecho/data/dao/DebtDao;", "(Lcom/expenseecho/data/dao/DebtDao;)V", "calculateMonthsToPayoff", "", "debt", "Lcom/expenseecho/data/entity/Debt;", "extraPayment", "", "deactivateDebt", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDebt", "(Lcom/expenseecho/data/entity/Debt;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllActiveDebts", "Lkotlinx/coroutines/flow/Flow;", "", "getAllDebts", "getDebtAvalancheOrder", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDebtById", "getTotalDebt", "getTotalMinPayments", "insertDebt", "updateDebt", "updateDebtBalance", "newBalance", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DebtRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.dao.DebtDao debtDao = null;
    
    @javax.inject.Inject()
    public DebtRepository(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.dao.DebtDao debtDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.expenseecho.data.entity.Debt>> getAllActiveDebts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.expenseecho.data.entity.Debt>> getAllDebts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDebtById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.expenseecho.data.entity.Debt> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTotalDebt(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTotalMinPayments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertDebt(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Debt debt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateDebt(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Debt debt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteDebt(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Debt debt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deactivateDebt(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateDebtBalance(@org.jetbrains.annotations.NotNull()
    java.lang.String id, long newBalance, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Calculate debt avalanche order (highest APR first)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDebtAvalancheOrder(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.expenseecho.data.entity.Debt>> $completion) {
        return null;
    }
    
    /**
     * Calculate months to payoff for a given extra payment amount
     */
    public final int calculateMonthsToPayoff(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Debt debt, long extraPayment) {
        return 0;
    }
}