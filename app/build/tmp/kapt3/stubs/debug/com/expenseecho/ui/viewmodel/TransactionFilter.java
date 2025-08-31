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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "", "()V", "All", "Category", "DateRange", "Expense", "Income", "Transfer", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$All;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$Category;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$DateRange;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$Expense;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$Income;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter$Transfer;", "app_debug"})
public abstract class TransactionFilter {
    
    private TransactionFilter() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$All;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "()V", "app_debug"})
    public static final class All extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        public static final com.expenseecho.ui.viewmodel.TransactionFilter.All INSTANCE = null;
        
        private All() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$Category;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "categoryId", "", "(Ljava/lang/String;)V", "getCategoryId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Category extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String categoryId = null;
        
        public Category(@org.jetbrains.annotations.NotNull()
        java.lang.String categoryId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCategoryId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.expenseecho.ui.viewmodel.TransactionFilter.Category copy(@org.jetbrains.annotations.NotNull()
        java.lang.String categoryId) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$DateRange;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "startDate", "Ljava/time/LocalDate;", "endDate", "(Ljava/time/LocalDate;Ljava/time/LocalDate;)V", "getEndDate", "()Ljava/time/LocalDate;", "getStartDate", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class DateRange extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalDate startDate = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalDate endDate = null;
        
        public DateRange(@org.jetbrains.annotations.NotNull()
        java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate endDate) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate getStartDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate getEndDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.expenseecho.ui.viewmodel.TransactionFilter.DateRange copy(@org.jetbrains.annotations.NotNull()
        java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate endDate) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$Expense;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "()V", "app_debug"})
    public static final class Expense extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        public static final com.expenseecho.ui.viewmodel.TransactionFilter.Expense INSTANCE = null;
        
        private Expense() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$Income;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "()V", "app_debug"})
    public static final class Income extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        public static final com.expenseecho.ui.viewmodel.TransactionFilter.Income INSTANCE = null;
        
        private Income() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/expenseecho/ui/viewmodel/TransactionFilter$Transfer;", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "()V", "app_debug"})
    public static final class Transfer extends com.expenseecho.ui.viewmodel.TransactionFilter {
        @org.jetbrains.annotations.NotNull()
        public static final com.expenseecho.ui.viewmodel.TransactionFilter.Transfer INSTANCE = null;
        
        private Transfer() {
        }
    }
}