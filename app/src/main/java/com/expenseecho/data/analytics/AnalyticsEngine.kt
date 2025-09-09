package com.expenseecho.data.analytics

import com.expenseecho.data.entity.Category
import com.expenseecho.data.entity.Transaction
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.CategoryRepository
import com.expenseecho.data.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsEngine @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetRepository: BudgetRepository
) {
    
    /**
     * Get comprehensive analytics for a given month
     */
    suspend fun getMonthlyAnalytics(month: YearMonth, transactionFilter: TransactionTypeFilter = TransactionTypeFilter.ALL_EXPENSES): MonthlyAnalytics {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        val categories = categoryRepository.getAllCategories().first()
        
        return MonthlyAnalytics(
            month = month,
            categoryAnalysis = getCategoryAnalysis(transactions, categories, transactionFilter),
            trendData = getTrendData(month, transactions, transactionFilter),
            merchantAnalysis = getMerchantAnalysis(transactions, categories, transactionFilter),
            monthlyComparison = getMonthlyComparison(month),
            spendingPatterns = getSpendingPatterns(transactions),
            budgetAnalysis = getBudgetAnalysis(month, categories)
        )
    }
    
    /**
     * Get at-a-glance summary for quick insights
     */
    suspend fun getAtAGlanceSummary(month: YearMonth): AtAGlanceSummary {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        val totalIncome = transactionRepository.getTotalIncome(startDate, endDate)
        val totalExpenses = transactionRepository.getTotalExpenses(startDate, endDate)
        val netAmount = totalIncome - totalExpenses
        val savingsRate = if (totalIncome > 0) (netAmount.toFloat() / totalIncome.toFloat()) * 100f else 0f
        
        val topCategory = getTopSpendingCategory(month)
        val biggestExpense = getBiggestExpense(month)
        val averageDailySpending = if (totalExpenses > 0) totalExpenses / month.lengthOfMonth() else 0L
        
        return AtAGlanceSummary(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            netAmount = netAmount,
            savingsRate = savingsRate,
            topCategory = topCategory,
            biggestExpense = biggestExpense,
            averageDailySpending = averageDailySpending,
            transactionCount = transactionRepository.getTransactionCount()
        )
    }
    
    /**
     * Get data for custom graphs
     */
    suspend fun getGraphData(
        dataSource: DataSource,
        month: YearMonth,
        filters: Map<String, Any> = emptyMap()
    ): List<AnalyticsData> {
        return when (dataSource) {
            DataSource.CATEGORY_SPENDING -> getCategoryAnalysis(
                transactionRepository.getTransactionsByDateRange(month.atDay(1), month.atEndOfMonth()).first(),
                categoryRepository.getAllCategories().first()
            )
            DataSource.MONTHLY_TRENDS -> getTrendData(month, emptyList())
            DataSource.MERCHANT_ANALYSIS -> getMerchantAnalysis(
                transactionRepository.getTransactionsByDateRange(month.atDay(1), month.atEndOfMonth()).first(),
                categoryRepository.getAllCategories().first()
            )
            DataSource.DAILY_PATTERNS -> getSpendingPatterns(
                transactionRepository.getTransactionsByDateRange(month.atDay(1), month.atEndOfMonth()).first()
            )
            DataSource.BUDGET_VS_ACTUAL -> getBudgetAnalysis(month, categoryRepository.getAllCategories().first())
            DataSource.INCOME_VS_EXPENSES -> getIncomeVsExpensesData(month)
            DataSource.TOP_MERCHANTS -> getTopMerchantsData(month)
            DataSource.SPENDING_BY_DAY_OF_WEEK -> getSpendingByDayOfWeek(month)
        }
    }
    
    private suspend fun getCategoryAnalysis(
        transactions: List<Transaction>,
        categories: List<Category>,
        transactionFilter: TransactionTypeFilter = TransactionTypeFilter.ALL_EXPENSES
    ): List<CategoryAnalysis> {
        // Filter transactions based on the selected filter
        val spendingTransactions = when (transactionFilter) {
            TransactionTypeFilter.ALL_EXPENSES -> transactions.filter { it.type == "Expense" }
            TransactionTypeFilter.PURCHASES_ONLY -> transactions.filter { it.type == "Expense" && it.paymentMethod == "Debit Card" }
            TransactionTypeFilter.TRANSFERS_ONLY -> transactions.filter { it.type == "Expense" && it.paymentMethod == "Bank Transfer" }
        }
        val totalSpending = spendingTransactions.sumOf { it.amount }
        val results = mutableListOf<CategoryAnalysis>()
        
        // Process categorized transactions
        categories.forEach { category ->
            val categoryTransactions = spendingTransactions.filter { it.categoryId == category.id }
            val amount = categoryTransactions.sumOf { it.amount }
            
            if (amount > 0) {
                results.add(
                    CategoryAnalysis(
                        category = category,
                        amount = amount,
                        percentage = if (totalSpending > 0) (amount.toFloat() / totalSpending.toFloat()) * 100f else 0f,
                        transactionCount = categoryTransactions.size,
                        averageTransaction = amount / categoryTransactions.size
                    )
                )
            }
        }
        
        // Add uncategorized transactions
        val uncategorizedTransactions = spendingTransactions.filter { it.categoryId == null }
        val uncategorizedAmount = uncategorizedTransactions.sumOf { it.amount }
        
        if (uncategorizedAmount > 0) {
            // Create a special "Uncategorized" category
            val uncategorizedCategory = Category(
                id = "uncategorized",
                name = "Uncategorized",
                isBudgetable = false,
                color = "#9E9E9E", // Gray color
                icon = "help_outline"
            )
            
            results.add(
                CategoryAnalysis(
                    category = uncategorizedCategory,
                    amount = uncategorizedAmount,
                    percentage = if (totalSpending > 0) (uncategorizedAmount.toFloat() / totalSpending.toFloat()) * 100f else 0f,
                    transactionCount = uncategorizedTransactions.size,
                    averageTransaction = uncategorizedAmount / uncategorizedTransactions.size
                )
            )
        }
        
        return results.sortedByDescending { it.amount }
    }
    
    private suspend fun getTrendData(month: YearMonth, transactions: List<Transaction>, transactionFilter: TransactionTypeFilter = TransactionTypeFilter.ALL_EXPENSES): List<TrendData> {
        val weeks = mutableListOf<TrendData>()
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        var currentDate = startDate
        var weekNumber = 1
        
        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            val weekEnd = currentDate.plusDays(6).let { if (it.isAfter(endDate)) endDate else it }
            
            val weekTransactions = transactionRepository.getTransactionsByDateRange(currentDate, weekEnd).first()
            val income = weekTransactions.filter { it.type == "Income" }.sumOf { it.amount }
            
            // Filter expenses based on transaction filter
            val expenses = when (transactionFilter) {
                TransactionTypeFilter.ALL_EXPENSES -> weekTransactions.filter { it.type == "Expense" }.sumOf { it.amount }
                TransactionTypeFilter.PURCHASES_ONLY -> weekTransactions.filter { it.type == "Expense" && it.paymentMethod == "Debit Card" }.sumOf { it.amount }
                TransactionTypeFilter.TRANSFERS_ONLY -> weekTransactions.filter { it.type == "Expense" && it.paymentMethod == "Bank Transfer" }.sumOf { it.amount }
            }
            
            weeks.add(
                TrendData(
                    period = "Week $weekNumber",
                    income = income,
                    expenses = expenses,
                    net = income - expenses
                )
            )
            
            currentDate = weekEnd.plusDays(1)
            weekNumber++
        }
        
        return weeks
    }
    
    private suspend fun getMerchantAnalysis(
        transactions: List<Transaction>,
        categories: List<Category>,
        transactionFilter: TransactionTypeFilter = TransactionTypeFilter.ALL_EXPENSES
    ): List<MerchantAnalysis> {
        // Filter transactions based on the selected filter
        val filteredTransactions = when (transactionFilter) {
            TransactionTypeFilter.ALL_EXPENSES -> transactions.filter { it.type == "Expense" }
            TransactionTypeFilter.PURCHASES_ONLY -> transactions.filter { it.type == "Expense" && it.paymentMethod == "Debit Card" }
            TransactionTypeFilter.TRANSFERS_ONLY -> transactions.filter { it.type == "Expense" && it.paymentMethod == "Bank Transfer" }
        }
        
        val merchantMap = mutableMapOf<String, MutableList<Transaction>>()
        
        filteredTransactions.forEach { transaction ->
            transaction.merchant?.let { merchant ->
                merchantMap.getOrPut(merchant) { mutableListOf() }.add(transaction)
            }
        }
        
        return merchantMap.map { (merchantName, merchantTransactions) ->
            val totalAmount = merchantTransactions.sumOf { it.amount }
            val category = merchantTransactions.firstOrNull()?.categoryId?.let { categoryId ->
                categories.find { it.id == categoryId }
            }
            
            MerchantAnalysis(
                merchantName = merchantName,
                totalAmount = totalAmount,
                transactionCount = merchantTransactions.size,
                category = category,
                lastTransactionDate = merchantTransactions.maxOf { it.date }
            )
        }.sortedByDescending { it.totalAmount }
    }
    
    private suspend fun getMonthlyComparison(month: YearMonth): MonthlyComparison {
        val previousMonth = month.minusMonths(1)
        
        val currentIncome = transactionRepository.getTotalIncome(month.atDay(1), month.atEndOfMonth())
        val currentExpenses = transactionRepository.getTotalExpenses(month.atDay(1), month.atEndOfMonth())
        val currentNet = currentIncome - currentExpenses
        val currentSavingsRate = if (currentIncome > 0) (currentNet.toFloat() / currentIncome.toFloat()) * 100f else 0f
        
        val previousIncome = transactionRepository.getTotalIncome(previousMonth.atDay(1), previousMonth.atEndOfMonth())
        val previousExpenses = transactionRepository.getTotalExpenses(previousMonth.atDay(1), previousMonth.atEndOfMonth())
        val previousNet = previousIncome - previousExpenses
        val previousSavingsRate = if (previousIncome > 0) (previousNet.toFloat() / previousIncome.toFloat()) * 100f else 0f
        
        return MonthlyComparison(
            currentMonth = month,
            previousMonth = previousMonth,
            incomeChange = calculatePercentageChange(previousIncome, currentIncome),
            expenseChange = calculatePercentageChange(previousExpenses, currentExpenses),
            netChange = calculatePercentageChange(previousNet, currentNet),
            savingsRateChange = currentSavingsRate - previousSavingsRate
        )
    }
    
    private suspend fun getSpendingPatterns(transactions: List<Transaction>): List<SpendingPattern> {
        val dayOfWeekMap = mutableMapOf<DayOfWeek, MutableList<Transaction>>()
        
        transactions.filter { it.type == "Expense" }.forEach { transaction ->
            val dayOfWeek = transaction.date.dayOfWeek
            dayOfWeekMap.getOrPut(dayOfWeek) { mutableListOf() }.add(transaction)
        }
        
        val totalByDay = dayOfWeekMap.mapValues { (_, transactions) ->
            transactions.sumOf { it.amount }
        }
        val maxAmount = totalByDay.values.maxOrNull() ?: 0L
        
        return DayOfWeek.values().map { dayOfWeek ->
            val dayTransactions = dayOfWeekMap[dayOfWeek] ?: emptyList()
            val amount = dayTransactions.sumOf { it.amount }
            
            SpendingPattern(
                dayOfWeek = dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() },
                averageAmount = if (dayTransactions.isNotEmpty()) amount / dayTransactions.size else 0L,
                transactionCount = dayTransactions.size,
                isPeakDay = amount == maxAmount && amount > 0
            )
        }
    }
    
    private suspend fun getBudgetAnalysis(month: YearMonth, categories: List<Category>): List<BudgetAnalysis> {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        
        return categories.filter { it.isBudgetable }.mapNotNull { category ->
            // For now, return null since we don't have budget functionality yet
            // This will be implemented when budget features are added
            null
        }
    }
    
    private suspend fun getTopSpendingCategory(month: YearMonth): CategoryAnalysis? {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        val categories = categoryRepository.getAllCategories().first()
        
        return getCategoryAnalysis(transactions, categories).firstOrNull()
    }
    
    private suspend fun getBiggestExpense(month: YearMonth): Transaction? {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        
        return transactions.filter { it.type == "Expense" }.maxByOrNull { it.amount }
    }
    
    private suspend fun getIncomeVsExpensesData(month: YearMonth): List<AnalyticsData> {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        
        val income = transactions.filter { it.type == "Income" }.sumOf { it.amount }
        val expenses = transactions.filter { it.type == "Expense" }.sumOf { it.amount }
        
        return listOf(
            TrendData("Income", income, 0, income),
            TrendData("Expenses", 0, expenses, -expenses)
        )
    }
    
    private suspend fun getTopMerchantsData(month: YearMonth): List<AnalyticsData> {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        val categories = categoryRepository.getAllCategories().first()
        
        return getMerchantAnalysis(transactions, categories).take(10)
    }
    
    private suspend fun getSpendingByDayOfWeek(month: YearMonth): List<AnalyticsData> {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        val transactions = transactionRepository.getTransactionsByDateRange(startDate, endDate).first()
        
        return getSpendingPatterns(transactions)
    }
    
    private fun calculatePercentageChange(oldValue: Long, newValue: Long): Float {
        return if (oldValue > 0) {
            ((newValue - oldValue).toFloat() / oldValue.toFloat()) * 100f
        } else if (newValue > 0) 100f else 0f
    }
}

/**
 * Comprehensive monthly analytics data
 */
data class MonthlyAnalytics(
    val month: YearMonth,
    val categoryAnalysis: List<CategoryAnalysis>,
    val trendData: List<TrendData>,
    val merchantAnalysis: List<MerchantAnalysis>,
    val monthlyComparison: MonthlyComparison,
    val spendingPatterns: List<SpendingPattern>,
    val budgetAnalysis: List<BudgetAnalysis>
)

/**
 * At-a-glance summary for quick insights
 */
data class AtAGlanceSummary(
    val totalIncome: Long,
    val totalExpenses: Long,
    val netAmount: Long,
    val savingsRate: Float,
    val topCategory: CategoryAnalysis?,
    val biggestExpense: Transaction?,
    val averageDailySpending: Long,
    val transactionCount: Int
)
