package com.expenseecho

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expenseecho.service.SmsMonitoringService
import com.expenseecho.ui.navigation.ExpenseEchoNavigation
import com.expenseecho.ui.theme.ExpenseEchoTheme
import com.expenseecho.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            ExpenseEchoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseEchoNavigation()
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "🔄 App resumed - triggering SMS sync")
        // Try to start SMS monitoring service, but don't crash if it fails
        try {
            SmsMonitoringService.startService(this)
            Log.d(TAG, "🔄 Started SMS monitoring service")
        } catch (e: Exception) {
            Log.e(TAG, "💥 Failed to start SMS monitoring service: ${e.message}")
            // Fallback: rely on WorkManager and manual refresh
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Don't stop the service on destroy - let it run in background
        Log.d(TAG, "🔄 MainActivity destroyed - keeping SMS monitoring service running")
    }
}
