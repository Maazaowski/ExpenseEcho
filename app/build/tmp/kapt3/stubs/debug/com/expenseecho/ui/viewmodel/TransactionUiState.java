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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\rH\u00c6\u0003JW\u0010\u001e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\r2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u000bH\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0012R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010\u00a8\u0006$"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionUiState;", "", "transactions", "", "Lcom/expenseecho/data/entity/Transaction;", "categories", "Lcom/expenseecho/data/entity/Category;", "filteredTransactions", "selectedFilter", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "searchQuery", "", "isLoading", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/expenseecho/ui/viewmodel/TransactionFilter;Ljava/lang/String;Z)V", "getCategories", "()Ljava/util/List;", "getFilteredTransactions", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSelectedFilter", "()Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "getTransactions", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class TransactionUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.expenseecho.data.entity.Transaction> transactions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.expenseecho.data.entity.Category> categories = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.expenseecho.data.entity.Transaction> filteredTransactions = null;
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.ui.viewmodel.TransactionFilter selectedFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    private final boolean isLoading = false;
    
    public TransactionUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Transaction> transactions, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Category> categories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Transaction> filteredTransactions, @org.jetbrains.annotations.NotNull()
    com.expenseecho.ui.viewmodel.TransactionFilter selectedFilter, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoading) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Transaction> getTransactions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Category> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Transaction> getFilteredTransactions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.ui.viewmodel.TransactionFilter getSelectedFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public TransactionUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Transaction> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Category> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.expenseecho.data.entity.Transaction> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.ui.viewmodel.TransactionFilter component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.ui.viewmodel.TransactionUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Transaction> transactions, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Category> categories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.expenseecho.data.entity.Transaction> filteredTransactions, @org.jetbrains.annotations.NotNull()
    com.expenseecho.ui.viewmodel.TransactionFilter selectedFilter, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoading) {
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