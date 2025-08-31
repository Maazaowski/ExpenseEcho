package com.expenseecho.ui.screen.dashboard;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.expenseecho.ui.theme.*;
import com.expenseecho.ui.viewmodel.DashboardViewModel;
import java.time.format.DateTimeFormatter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a(\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0003\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001a<\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"CategorySpendingItem", "", "categorySpending", "Lcom/expenseecho/ui/viewmodel/CategorySpending;", "DashboardScreen", "viewModel", "Lcom/expenseecho/ui/viewmodel/DashboardViewModel;", "FinancialSummaryCards", "income", "", "expenses", "net", "savingsRate", "", "MonthSelector", "currentMonth", "Ljava/time/YearMonth;", "onPreviousMonth", "Lkotlin/Function0;", "onNextMonth", "SummaryCard", "title", "", "amount", "color", "Landroidx/compose/ui/graphics/Color;", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "modifier", "Landroidx/compose/ui/Modifier;", "SummaryCard-XO-JAsU", "(Ljava/lang/String;JJLandroidx/compose/ui/graphics/vector/ImageVector;Landroidx/compose/ui/Modifier;)V", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull()
    com.expenseecho.ui.viewmodel.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MonthSelector(java.time.YearMonth currentMonth, kotlin.jvm.functions.Function0<kotlin.Unit> onPreviousMonth, kotlin.jvm.functions.Function0<kotlin.Unit> onNextMonth) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FinancialSummaryCards(long income, long expenses, long net, float savingsRate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CategorySpendingItem(com.expenseecho.ui.viewmodel.CategorySpending categorySpending) {
    }
}