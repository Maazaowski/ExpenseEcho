package com.expenseecho.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.expenseecho.MainActivity
import com.expenseecho.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SmsMonitoringService : Service() {
    
    @Inject
    lateinit var smsReaderService: SmsReaderService
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var monitoringJob: Job? = null
    
    companion object {
        private const val TAG = "SmsMonitoringService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "sms_monitoring_channel"
        private const val SYNC_INTERVAL_MS = 30_000L // 30 seconds
        
        fun startService(context: Context) {
            val intent = Intent(context, SmsMonitoringService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, SmsMonitoringService::class.java)
            context.stopService(intent)
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "🔄 SMS Monitoring Service created")
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "🔄 SMS Monitoring Service started")
        startForeground(NOTIFICATION_ID, createNotification())
        startMonitoring()
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "🔄 SMS Monitoring Service destroyed")
        monitoringJob?.cancel()
        serviceScope.cancel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SMS Monitoring",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Monitors SMS for transaction updates"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ExpenseEcho")
            .setContentText("Monitoring SMS for transactions...")
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
    
    private fun startMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = serviceScope.launch {
            while (isActive) {
                try {
                    Log.d(TAG, "🔄 Checking for new SMS messages...")
                    smsReaderService.refreshRecentTransactions()
                    delay(SYNC_INTERVAL_MS)
                } catch (e: Exception) {
                    Log.e(TAG, "💥 Error in SMS monitoring: ${e.message}", e)
                    delay(SYNC_INTERVAL_MS)
                }
            }
        }
    }
}
