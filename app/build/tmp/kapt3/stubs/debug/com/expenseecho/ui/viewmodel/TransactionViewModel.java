package com.expenseecho.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.expenseecho.data.entity.Transaction;
import com.expenseecho.data.entity.Category;
import com.expenseecho.data.repository.TransactionRepository;
import com.expenseecho.data.repository.CategoryRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u001a2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u000bH\u0002J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u000bJ\u000e\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\tR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006 "}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionViewModel;", "Landroidx/lifecycle/ViewModel;", "transactionRepository", "Lcom/expenseecho/data/repository/TransactionRepository;", "categoryRepository", "Lcom/expenseecho/data/repository/CategoryRepository;", "(Lcom/expenseecho/data/repository/TransactionRepository;Lcom/expenseecho/data/repository/CategoryRepository;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedFilter", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "searchQuery", "Lkotlinx/coroutines/flow/StateFlow;", "getSearchQuery", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedFilter", "getSelectedFilter", "uiState", "Lcom/expenseecho/ui/viewmodel/TransactionUiState;", "getUiState", "deleteTransaction", "", "transaction", "Lcom/expenseecho/data/entity/Transaction;", "filterTransactions", "", "transactions", "query", "filter", "updateFilter", "updateSearchQuery", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TransactionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.repository.CategoryRepository categoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.expenseecho.ui.viewmodel.TransactionFilter> _selectedFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.TransactionFilter> selectedFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.TransactionUiState> uiState = null;
    
    @javax.inject.Inject()
    public TransactionViewModel(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.CategoryRepository categoryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.TransactionFilter> getSelectedFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.expenseecho.ui.viewmodel.TransactionUiState> getUiState() {
        return null;
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void updateFilter(@org.jetbrains.annotations.NotNull()
    com.expenseecho.ui.viewmodel.TransactionFilter filter) {
    }
    
    public final void deleteTransaction(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Transaction transaction) {
    }
    
    private final java.util.List<com.expenseecho.data.entity.Transaction> filterTransactions(java.util.List<com.expenseecho.data.entity.Transaction> transactions, java.lang.String query, com.expenseecho.ui.viewmodel.TransactionFilter filter) {
        return null;
    }
}