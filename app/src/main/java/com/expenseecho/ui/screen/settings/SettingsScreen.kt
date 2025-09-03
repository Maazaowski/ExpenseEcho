package com.expenseecho.ui.screen.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expenseecho.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // SMS permission launcher
    val smsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onSmsPermissionGranted()
        }
    }
    
    // Check if SMS permission is granted
    val hasSmsPermission = ContextCompat.checkSelfPermission(
        context, 
        Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // SMS Access & Import Setup
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (hasSmsPermission) 
                        MaterialTheme.colorScheme.primaryContainer
                    else 
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (hasSmsPermission) Icons.Default.Sms else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (hasSmsPermission) 
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else 
                                MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "SMS Access",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (hasSmsPermission) 
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else 
                                MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = if (hasSmsPermission)
                            "SMS access granted. You can now import previous transactions and receive new ones automatically."
                        else
                            "Grant SMS access to read bank messages and import transactions automatically.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (hasSmsPermission) 
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else 
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (!hasSmsPermission) {
                        Button(
                            onClick = {
                                smsPermissionLauncher.launch(Manifest.permission.READ_SMS)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Grant SMS Permission")
                        }
                    } else {
                        // SMS Import Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.importRecentSms() },
                                modifier = Modifier.weight(1f),
                                enabled = !uiState.isImporting
                            ) {
                                if (uiState.isImporting && uiState.importType == "recent") {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text("Last 30 Days")
                                }
                            }
                            
                            Button(
                                onClick = { viewModel.importAllSms() },
                                modifier = Modifier.weight(1f),
                                enabled = !uiState.isImporting
                            ) {
                                if (uiState.isImporting && uiState.importType == "all") {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text("Import All")
                                }
                            }
                        }
                        
                        if (uiState.isImporting) {
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = uiState.importProgress,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Importing SMS... ${(uiState.importProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        
                        if (uiState.importMessage.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.importMessage,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        
                        if (uiState.dbStats.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = uiState.dbStats,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
        
        // Privacy & Security section
        item {
            Text(
                text = "Privacy & Security",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Security,
                title = "Data Encryption",
                subtitle = "Your data is encrypted locally using SQLCipher",
                onClick = { /* TODO: Show encryption info */ }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Backup,
                title = "Export Data",
                subtitle = "Create encrypted backup of your transactions",
                onClick = { /* TODO: Export data */ }
            )
        }
        
        // Categories & Rules section
        item {
            Text(
                text = "Categorization",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Category,
                title = "Manage Categories",
                subtitle = "Add, edit, or remove transaction categories",
                onClick = { /* TODO: Manage categories */ }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Rule,
                title = "Auto-categorization Rules",
                subtitle = "Set up keywords to automatically categorize transactions",
                onClick = { /* TODO: Manage rules */ }
            )
        }
        
        // About section
        item {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Info,
                title = "About ExpenseEcho",
                subtitle = "Version 1.0 - Local expense tracking",
                onClick = { /* TODO: Show about */ }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.PrivacyTip,
                title = "Privacy Policy",
                subtitle = "Your data never leaves your device",
                onClick = { /* TODO: Show privacy policy */ }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
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
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
