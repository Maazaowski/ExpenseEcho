package com.expenseecho.ui.screen.merchant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expenseecho.data.entity.Merchant
import com.expenseecho.data.entity.Category
import com.expenseecho.ui.theme.*
import com.expenseecho.ui.viewmodel.MerchantViewModel
import com.expenseecho.ui.viewmodel.MerchantFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchantListScreen(
    onNavigateBack: () -> Unit,
    viewModel: MerchantViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Merchants")
                        if (uiState.totalCount > 0) {
                            Text(
                                text = "${uiState.uncategorizedCount} uncategorized of ${uiState.totalCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                label = { Text("Search merchants") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filter chips
            MerchantFilterChips(
                selectedFilter = uiState.selectedFilter,
                categories = uiState.categories,
                uncategorizedCount = uiState.uncategorizedCount,
                onFilterSelected = viewModel::updateFilter
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Merchant list
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.filteredMerchants.isEmpty() -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Store,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (uiState.searchQuery.isNotBlank()) "No merchants found" else "No merchants yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (uiState.searchQuery.isNotBlank()) 
                                    "Try adjusting your search terms"
                                else 
                                    "Merchants will appear here as transactions are processed",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.filteredMerchants) { merchant ->
                            MerchantItem(
                                merchant = merchant,
                                categories = uiState.categories,
                                onCategoryChanged = { categoryId ->
                                    viewModel.updateMerchantCategory(merchant.id, categoryId)
                                },
                                onDelete = { viewModel.deleteMerchant(merchant) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MerchantFilterChips(
    selectedFilter: MerchantFilter,
    categories: List<Category>,
    uncategorizedCount: Int,
    onFilterSelected: (MerchantFilter) -> Unit
) {
    LazyColumn {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // All filter
                FilterChip(
                    onClick = { onFilterSelected(MerchantFilter.All) },
                    label = { Text("All") },
                    selected = selectedFilter is MerchantFilter.All
                )
                
                // Uncategorized filter
                FilterChip(
                    onClick = { onFilterSelected(MerchantFilter.Uncategorized) },
                    label = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Uncategorized")
                            if (uncategorizedCount > 0) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Badge {
                                    Text(uncategorizedCount.toString())
                                }
                            }
                        }
                    },
                    selected = selectedFilter is MerchantFilter.Uncategorized
                )
            }
        }
        
        // Category filters
        if (categories.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.take(4).forEach { category ->
                        FilterChip(
                            onClick = { 
                                onFilterSelected(MerchantFilter.Category(category.id, category.name)) 
                            },
                            label = { Text(category.name) },
                            selected = selectedFilter is MerchantFilter.Category && 
                                      selectedFilter.categoryId == category.id
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MerchantItem(
    merchant: Merchant,
    categories: List<Category>,
    onCategoryChanged: (String?) -> Unit,
    onDelete: () -> Unit
) {
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    val category = categories.find { it.id == merchant.categoryId }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Merchant info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = merchant.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Transaction count
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${merchant.transactionCount} transactions",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Delete button
                IconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete merchant",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Category selection
            Card(
                onClick = { showCategoryDialog = true },
                colors = CardDefaults.cardColors(
                    containerColor = if (category != null) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (category != null) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                        else 
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = category?.name ?: "Uncategorized",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (category != null) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                        else 
                            MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change category",
                        modifier = Modifier.size(16.dp),
                        tint = if (category != null) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                        else 
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
    
    // Category selection dialog
    if (showCategoryDialog) {
        CategorySelectionDialog(
            categories = categories,
            selectedCategoryId = merchant.categoryId,
            onCategorySelected = { categoryId ->
                onCategoryChanged(categoryId)
                showCategoryDialog = false
            },
            onDismiss = { showCategoryDialog = false }
        )
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Merchant") },
            text = { 
                Text("Are you sure you want to delete \"${merchant.name}\"? This will not affect existing transactions.") 
            },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
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
                
                // Category options
                items(categories) { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
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
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}
