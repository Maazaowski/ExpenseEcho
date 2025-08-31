package com.expenseecho.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.expenseecho.data.repository.TransactionRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsNotificationListenerService : NotificationListenerService() {
    
    @Inject
    lateinit var transactionRepository: TransactionRepository
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    companion object {
        private const val TAG = "SmsNotifListener"
        private const val TARGET_ACCOUNT_MASK = "6809" // Your account ends with 6809
        private const val BANK_SMS_NUMBER = "8287" // Your bank's SMS number
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        try {
            val packageName = sbn.packageName ?: return
            
            // Only process SMS app notifications
            if (!isSmsApp(packageName)) return
            
            val notification = sbn.notification
            val extras = notification.extras
            
            // Extract notification content
            val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
            val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
            val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: ""
            
            // Combine all text content
            val combinedText = listOf(title, text, bigText, subText)
                .filter { it.isNotBlank() }
                .joinToString("\n")
                .trim()
            
            if (combinedText.isBlank()) return
            
            // Check if this SMS is from your bank (optional additional filter)
            val isBankSms = title.contains(BANK_SMS_NUMBER) || 
                           combinedText.contains("Dear Customer") ||
                           combinedText.contains("BAF A/C") ||
                           combinedText.contains("Debit Card")
            
            Log.d(TAG, "Processing SMS notification: $combinedText")
            Log.d(TAG, "Is bank SMS: $isBankSms")
            
            // Parse the SMS content
            val parsedSms = SmsParser.parse(combinedText)
            if (parsedSms == null) {
                Log.d(TAG, "Failed to parse SMS: $combinedText")
                return
            }
            
            // Filter: only store transactions for target account ending with 6809
            if (parsedSms.accountMask?.contains(TARGET_ACCOUNT_MASK) != true) {
                Log.d(TAG, "Ignoring SMS for different account: ${parsedSms.accountMask}")
                return
            }
            
            // Store the transaction
            serviceScope.launch {
                try {
                    val transaction = parsedSms.toTransaction()
                    transactionRepository.insertTransaction(transaction)
                    Log.d(TAG, "Successfully stored transaction: ${transaction.id}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to store transaction", e)
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing notification", e)
        }
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Not needed for our use case
    }
    
    private fun isSmsApp(packageName: String): Boolean {
        return when (packageName) {
            // Google Messages
            "com.google.android.apps.messaging" -> true
            
            // Samsung Messages
            "com.samsung.android.messaging" -> true
            
            // OnePlus Messages
            "com.oneplus.mms" -> true
            
            // Huawei Messages
            "com.huawei.mms" -> true
            
            // Xiaomi Messages
            "com.miui.mms" -> true
            
            // HTC Messages
            "com.htc.sense.mms" -> true
            
            // Sony Messages
            "com.sonymobile.conversations" -> true
            
            // LG Messages
            "com.lge.message" -> true
            
            // Oppo Messages
            "com.oppo.mms" -> true
            
            // Vivo Messages
            "com.vivo.mms" -> true
            
            // Generic fallback for other messaging apps
            else -> packageName.contains("messag", ignoreCase = true) ||
                    packageName.contains("sms", ignoreCase = true) ||
                    packageName.contains("mms", ignoreCase = true)
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SMS Notification Listener Service created")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SMS Notification Listener Service destroyed")
    }
    
    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Notification listener connected")
    }
    
    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "Notification listener disconnected")
    }
}
