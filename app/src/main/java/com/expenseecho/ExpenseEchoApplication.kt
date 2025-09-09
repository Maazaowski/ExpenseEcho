package com.expenseecho

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.expenseecho.service.SmsReaderService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ExpenseEchoApplication : Application() {
    
    @Inject
    lateinit var smsReaderService: SmsReaderService
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        
        // Start periodic SMS sync as backup
        startPeriodicSmsSync()
    }
    
    private fun startPeriodicSmsSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()
        
        val periodicSyncRequest = PeriodicWorkRequestBuilder<SmsSyncWorker>(2, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sms_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
        
        Log.d("ExpenseEchoApplication", "🔄 Started periodic SMS sync (every 2 minutes)")
    }
}

class SmsSyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    
    @Inject
    lateinit var smsReaderService: SmsReaderService
    
    override suspend fun doWork(): Result {
        return try {
            Log.d("SmsSyncWorker", "🔄 Periodic SMS sync started")
            smsReaderService.refreshRecentTransactions()
            Log.d("SmsSyncWorker", "✅ Periodic SMS sync completed")
            Result.success()
        } catch (e: Exception) {
            Log.e("SmsSyncWorker", "💥 Periodic SMS sync failed: ${e.message}", e)
            Result.retry()
        }
    }
}
