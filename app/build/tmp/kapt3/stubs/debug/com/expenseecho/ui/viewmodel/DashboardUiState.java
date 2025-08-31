package com.expenseecho.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.expenseecho.data.repository.TransactionRepository;
import com.expenseecho.data.repository.CategoryRepository;
import com.expenseecho.data.repository.BudgetRepository;
import com.expenseecho.data.entity.Category;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\t\u0010$\u001a\u00020\fH\u00c6\u0003J\t\u0010%\u001a\u00020\u000eH\u00c6\u0003J\t\u0010&\u001a\u00020\u0010H\u00c6\u0003J_\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010(\u001a\u00020\u00102\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\fH\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018\u00a8\u0006-"}, d2 = {"Lcom/expenseecho/ui/viewmodel/DashboardUiState;", "", "totalIncome", "", "totalExpenses", "netAmount", "savingsRate", "", "categorySpending", "", "Lcom/expenseecho/ui/viewmodel/CategorySpending;", "recentTransactions", "", "currentMonth", "Ljava/time/YearMonth;", "isLoading", "", "(JJJFLjava/util/List;ILjava/time/YearMonth;Z)V", "getCategorySpending", "()Ljava/util/List;", "getCurrentMonth", "()Ljava/time/YearMonth;", "()Z", "getNetAmount", "()J", "getRecentTransactions", "()I", "getSavingsRate", "()F", "getTotalExpenses", "getTotalIncome", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class DashboardUiState {
    private final long totalIncome = 0L;
    private final long totalExpenses = 0L;
    private final long netAmount = 0L;
    private final float savingsRate = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.expenseecho.ui.viewmodel.CategorySpending> categorySpending = null;
    private final int recentTransactions = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.time.YearMonth currentMonth = null;
    private final boolean isLoading = false;
    
    public DashboardUiState(long totalIncome, long totalExpenses, long netAmount, float savingsRate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.ui.viewmodel.CategorySpending> categorySpending, int recentTransactions, @org.jetbrains.annotations.NotNull()
    java.time.YearMonth currentMonth, boolean isLoading) {
        super();
    }
    
    public final long getTotalIncome() {
        return 0L;
    }
    
    public final long getTotalExpenses() {
        return 0L;
    }
    
    public final long getNetAmount() {
        return 0L;
    }
    
    public final float getSavingsRate() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.ui.viewmodel.CategorySpending> getCategorySpending() {
        return null;
    }
    
    public final int getRecentTransactions() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.YearMonth getCurrentMonth() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public DashboardUiState() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.ui.viewmodel.CategorySpending> component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.YearMonth component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.ui.viewmodel.DashboardUiState copy(long totalIncome, long totalExpenses, long netAmount, float savingsRate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.ui.viewmodel.CategorySpending> categorySpending, int recentTransactions, @org.jetbrains.annotations.NotNull()
    java.time.YearMonth currentMonth, boolean isLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}