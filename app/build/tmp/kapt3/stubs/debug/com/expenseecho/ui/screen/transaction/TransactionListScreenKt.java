package com.expenseecho.ui.screen.transaction;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.expenseecho.data.entity.Transaction;
import com.expenseecho.ui.theme.*;
import com.expenseecho.ui.viewmodel.TransactionViewModel;
import com.expenseecho.ui.viewmodel.TransactionFilter;
import java.time.format.DateTimeFormatter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u0012\u0010\u000b\u001a\u00020\u00012\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"TransactionFilterChips", "", "selectedFilter", "Lcom/expenseecho/ui/viewmodel/TransactionFilter;", "onFilterSelected", "Lkotlin/Function1;", "TransactionItem", "transaction", "Lcom/expenseecho/data/entity/Transaction;", "onDelete", "Lkotlin/Function0;", "TransactionListScreen", "viewModel", "Lcom/expenseecho/ui/viewmodel/TransactionViewModel;", "app_debug"})
public final class TransactionListScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TransactionListScreen(@org.jetbrains.annotations.NotNull()
    com.expenseecho.ui.viewmodel.TransactionViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void TransactionFilterChips(com.expenseecho.ui.viewmodel.TransactionFilter selectedFilter, kotlin.jvm.functions.Function1<? super com.expenseecho.ui.viewmodel.TransactionFilter, kotlin.Unit> onFilterSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void TransactionItem(com.expenseecho.data.entity.Transaction transaction, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
}