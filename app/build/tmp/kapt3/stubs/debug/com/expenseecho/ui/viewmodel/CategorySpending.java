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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/expenseecho/ui/viewmodel/CategorySpending;", "", "category", "Lcom/expenseecho/data/entity/Category;", "amount", "", "percentage", "", "(Lcom/expenseecho/data/entity/Category;JF)V", "getAmount", "()J", "getCategory", "()Lcom/expenseecho/data/entity/Category;", "getPercentage", "()F", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class CategorySpending {
    @org.jetbrains.annotations.NotNull()
    private final com.expenseecho.data.entity.Category category = null;
    private final long amount = 0L;
    private final float percentage = 0.0F;
    
    public CategorySpending(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Category category, long amount, float percentage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.entity.Category getCategory() {
        return null;
    }
    
    public final long getAmount() {
        return 0L;
    }
    
    public final float getPercentage() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.entity.Category component1() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final float component3() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.ui.viewmodel.CategorySpending copy(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.entity.Category category, long amount, float percentage) {
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