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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u000bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u0010\u001d\u001a\u00020\u001bR\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010\u00a8\u0006\u001e"}, d2 = {"Lcom/expenseecho/ui/viewmodel/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "transactionRepository", "Lcom/expenseecho/data/repository/TransactionRepository;", "categoryRepository", "Lcom/expenseecho/data/repository/CategoryRepository;", "budgetRepository", "Lcom/expenseecho/data/repository/BudgetRepository;", "(Lcom/expenseecho/data/repository/TransactionRepository;Lcom/expenseecho/data/repository/CategoryRepository;Lcom/expenseecho/data/repository/BudgetRepository;)V", "_currentMonth", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Ljava/time/YearMonth;", "kotlin.jvm.PlatformType", "currentMonth", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentMonth", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "Lcom/expenseecho/ui/viewmodel/DashboardUiState;", "getUiState", "calculateDashboardData", "month", "categories", "", "Lcom/expenseecho/data/entity/Category;", "(Ljava/time/YearMonth;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changeMonth", "", "goToNextMonth", "goToPreviousMonth", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.repository.CategoryRepository categoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.repository.BudgetRepository budgetRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.YearMonth> _currentMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> currentMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.DashboardUiState> uiState = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.CategoryRepository categoryRepository, @org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.BudgetRepository budgetRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> getCurrentMonth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.DashboardUiState> getUiState() {
        return null;
    }
    
    public final void changeMonth(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth month) {
    }
    
    public final void goToPreviousMonth() {
    }
    
    public final void goToNextMonth() {
    }
    
    private final java.lang.Object calculateDashboardData(java.time.YearMonth month, java.util.List<com.expenseecho.data.entity.Category> categories, kotlin.coroutines.Continuation<? super com.expenseecho.ui.viewmodel.DashboardUiState> $completion) {
        return null;
    }
}