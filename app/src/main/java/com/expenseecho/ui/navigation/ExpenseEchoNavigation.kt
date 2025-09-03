package com.expenseecho.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.expenseecho.ui.screen.budget.BudgetScreen
import com.expenseecho.ui.screen.dashboard.DashboardScreen
import com.expenseecho.ui.screen.debt.DebtScreen
import com.expenseecho.ui.screen.settings.SettingsScreen
import com.expenseecho.ui.screen.transaction.TransactionListScreen
import com.expenseecho.ui.screen.transaction.TransactionDetailsScreen
import com.expenseecho.ui.screen.merchant.MerchantListScreen

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Transactions : Screen("transactions", "Transactions", Icons.Default.List)
    object Budget : Screen("budget", "Budget", Icons.Default.AccountBalanceWallet)
    object Debt : Screen("debt", "Debt", Icons.Default.CreditCard)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object TransactionDetails : Screen("transaction_details/{transactionId}", "Transaction Details", Icons.Default.Receipt)
    object Merchants : Screen("merchants", "Merchants", Icons.Default.Store)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEchoNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Dashboard,
        Screen.Transactions,
        Screen.Budget,
        Screen.Debt,
        Screen.Merchants,
        Screen.Settings
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }
            composable(Screen.Transactions.route) {
                TransactionListScreen(
                    onTransactionClick = { transactionId ->
                        navController.navigate("transaction_details/$transactionId")
                    }
                )
            }
            composable(Screen.Budget.route) {
                BudgetScreen()
            }
            composable(Screen.Debt.route) {
                DebtScreen()
            }
            composable(Screen.Merchants.route) {
                MerchantListScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
            composable(Screen.TransactionDetails.route) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getString("transactionId") ?: ""
                TransactionDetailsScreen(
                    transactionId = transactionId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
