package com.expenseecho.ui.screen.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expenseecho.data.entity.Transaction
import com.expenseecho.data.entity.Category
import com.expenseecho.data.entity.Account
import com.expenseecho.ui.theme.*
import com.expenseecho.ui.viewmodel.TransactionDetailsViewModel
import com.expenseecho.util.formatCurrency
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    transactionId: String,
    onNavigateBack: () -> Unit,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(transactionId) {
        viewModel.loadTransaction(transactionId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.transaction == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Transaction not found",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            else -> {
                TransactionDetailsContent(
                    transaction = uiState.transaction!!,
                    account = uiState.account,
                    category = uiState.category,
                    categories = uiState.categories,
                    onCategoryChanged = viewModel::updateCategory,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionDetailsContent(
    transaction: Transaction,
    account: Account?,
    category: Category?,
    categories: List<Category>,
    onCategoryChanged: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCategoryDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Transaction Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (transaction.type) {
                    "Income" -> IncomeGreen.copy(alpha = 0.1f)
                    "Expense" -> ExpenseRed.copy(alpha = 0.1f)
                    "Transfer" -> TransferBlue.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Transaction Type Icon
                val (color, icon) = when (transaction.type) {
                    "Income" -> IncomeGreen to Icons.Default.TrendingUp
                    "Expense" -> ExpenseRed to Icons.Default.TrendingDown
                    "Transfer" -> TransferBlue to Icons.Default.SwapHoriz
                    else -> MaterialTheme.colorScheme.onSurface to Icons.Default.AttachMoney
                }
                
                Icon(
                    imageVector = icon,
                    contentDescription = transaction.type,
                    tint = color,
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Amount
                Text(
                    text = if (transaction.type == "Expense") "-${formatCurrency(transaction.amount)}" 
                           else formatCurrency(transaction.amount),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                
                // Transaction Type
                Text(
                    text = transaction.type,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Date
                Text(
                    text = transaction.date.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Details Section
        Text(
            text = "Transaction Details",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Details Cards
        DetailCard(
            title = "Description",
            content = transaction.description ?: "No description",
            icon = Icons.Default.Description
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Account Info
        DetailCard(
            title = "Account",
            content = account?.getDisplayNameWithLastFour() ?: "Unknown Account",
            icon = Icons.Default.AccountBalance
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Payment Method
        DetailCard(
            title = "Payment Method",
            content = transaction.paymentMethod,
            icon = when (transaction.paymentMethod) {
                "Debit Card" -> Icons.Default.CreditCard
                "Bank Transfer" -> Icons.Default.AccountBalance
                else -> Icons.Default.Payment
            }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Merchant/Recipient
        if (transaction.merchant != null) {
            DetailCard(
                title = when (transaction.type) {
                    "Income" -> "From"
                    "Transfer" -> "To"
                    else -> "Merchant"
                },
                content = transaction.merchant,
                icon = when (transaction.type) {
                    "Income" -> Icons.Default.Person
                    "Transfer" -> Icons.Default.Person
                    else -> Icons.Default.Store
                }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
        
        // Category (Editable)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            onClick = { showCategoryDialog = true }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = category?.name ?: "Uncategorized",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit category",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Reference Number
        if (transaction.reference != null) {
            DetailCard(
                title = "Reference",
                content = transaction.reference,
                icon = Icons.Default.Receipt
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
        
        // Source
        DetailCard(
            title = "Source",
            content = if (transaction.source == "sms") "SMS Auto-detected" else "Manual Entry",
            icon = if (transaction.source == "sms") Icons.Default.Sms else Icons.Default.Edit
        )
        
        // Raw SMS (if available)
        if (transaction.rawText != null) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Original SMS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = transaction.rawText,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
    
    // Category Selection Dialog
    if (showCategoryDialog) {
        CategorySelectionDialog(
            categories = categories,
            selectedCategoryId = transaction.categoryId,
            onCategorySelected = { categoryId ->
                onCategoryChanged(categoryId)
                showCategoryDialog = false
            },
            onDismiss = { showCategoryDialog = false }
        )
    }
}

@Composable
private fun DetailCard(
    title: String,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun CategorySelectionDialog(
    categories: List<Category>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Category") },
        text = {
            LazyColumn {
                // Uncategorized option
                item {
                    TextButton(
                        onClick = { onCategorySelected(null) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategoryId == null,
                                onClick = { onCategorySelected(null) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Uncategorized")
                        }
                    }
                }
                
                // Category options
                items(categories.filter { it.isBudgetable }) { category ->
                    TextButton(
                        onClick = { onCategorySelected(category.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategoryId == category.id,
                                onClick = { onCategorySelected(category.id) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(category.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}
