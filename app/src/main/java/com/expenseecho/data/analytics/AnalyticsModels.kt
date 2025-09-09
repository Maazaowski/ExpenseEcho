package com.expenseecho.data.analytics

import com.expenseecho.data.entity.Category
import com.expenseecho.data.entity.Transaction
import java.time.LocalDate
import java.time.YearMonth

/**
 * Base interface for all analytics data points
 */
sealed interface AnalyticsData

/**
 * Category spending analysis
 */
data class CategoryAnalysis(
    val category: Category,
    val amount: Long,
    val percentage: Float,
    val transactionCount: Int,
    val averageTransaction: Long
) : AnalyticsData

/**
 * Time-based spending trend
 */
data class TrendData(
    val period: String, // "2024-01", "Week 1", etc.
    val income: Long,
    val expenses: Long,
    val net: Long
) : AnalyticsData

/**
 * Merchant spending analysis
 */
data class MerchantAnalysis(
    val merchantName: String,
    val totalAmount: Long,
    val transactionCount: Int,
    val category: Category?,
    val lastTransactionDate: LocalDate
) : AnalyticsData

/**
 * Monthly comparison data
 */
data class MonthlyComparison(
    val currentMonth: YearMonth,
    val previousMonth: YearMonth,
    val incomeChange: Float, // Percentage change
    val expenseChange: Float,
    val netChange: Float,
    val savingsRateChange: Float
) : AnalyticsData

/**
 * Spending pattern analysis
 */
data class SpendingPattern(
    val dayOfWeek: String,
    val averageAmount: Long,
    val transactionCount: Int,
    val isPeakDay: Boolean
) : AnalyticsData

/**
 * Budget vs actual analysis
 */
data class BudgetAnalysis(
    val category: Category,
    val budgetAmount: Long,
    val actualAmount: Long,
    val remainingAmount: Long,
    val utilizationPercentage: Float,
    val isOverBudget: Boolean
) : AnalyticsData

/**
 * Custom graph configuration
 */
data class CustomGraphConfig(
    val id: String,
    val title: String,
    val type: GraphType,
    val dataSource: DataSource,
    val filters: Map<String, Any> = emptyMap(),
    val isVisible: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Graph types available for customization
 */
enum class GraphType {
    PIE_CHART,
    BAR_CHART,
    LINE_CHART,
    DONUT_CHART,
    HORIZONTAL_BAR,
    AREA_CHART
}

/**
 * Data sources for graphs
 */
enum class DataSource {
    CATEGORY_SPENDING,
    MONTHLY_TRENDS,
    MERCHANT_ANALYSIS,
    DAILY_PATTERNS,
    BUDGET_VS_ACTUAL,
    INCOME_VS_EXPENSES,
    TOP_MERCHANTS,
    SPENDING_BY_DAY_OF_WEEK
}

/**
 * Transaction type filter for analytics
 */
enum class TransactionTypeFilter {
    ALL_EXPENSES,      // All money going out (purchases + transfers)
    PURCHASES_ONLY,    // Only actual purchases (not transfers)
    TRANSFERS_ONLY     // Only transfers (money moving between accounts)
}

/**
 * Pre-defined dashboard widgets
 */
enum class PredefinedWidget {
    AT_A_GLANCE_SUMMARY,
    CATEGORY_BREAKDOWN,
    MONTHLY_TREND,
    TOP_MERCHANTS,
    BUDGET_STATUS,
    SPENDING_PATTERNS,
    INCOME_VS_EXPENSES,
    SAVINGS_RATE_TREND
}
