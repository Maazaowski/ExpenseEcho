package com.expenseecho.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var smsReaderService: SmsReaderService
    
    companion object {
        private const val TAG = "SmsReceiver"
        private const val BANK_SMS_NUMBER = "8287" // Your bank's SMS number
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "📱 SMS received: ${intent.action}")
        
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            try {
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                Log.d(TAG, "📨 Processing ${smsMessages.size} SMS messages")
                
                for (smsMessage in smsMessages) {
                    val sender = smsMessage.originatingAddress
                    val body = smsMessage.messageBody
                    val timestamp = smsMessage.timestampMillis
                    
                    Log.d(TAG, "📨 SMS from: $sender, Body: ${body.take(100)}...")
                    
                    // Check if SMS is from bank
                    if (sender == BANK_SMS_NUMBER || sender?.contains("8287") == true) {
                        Log.d(TAG, "🏦 Bank SMS detected from: $sender")
                        
                        // Check if it contains bank transaction keywords
                        if (body.contains("Dear Customer", ignoreCase = true) || 
                            body.contains("BAF A/C", ignoreCase = true) ||
                            body.contains("Debit Card", ignoreCase = true) ||
                            body.contains("PKR", ignoreCase = true)) {
                            
                            Log.d(TAG, "✅ Bank transaction SMS detected, processing...")
                            // Process the SMS
                            smsReaderService.processSingleSms(body)
                        } else {
                            Log.d(TAG, "❌ Bank SMS but not a transaction (no keywords found)")
                        }
                    } else {
                        Log.d(TAG, "❌ SMS not from bank: $sender")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Error processing SMS: ${e.message}", e)
            }
        } else {
            Log.d(TAG, "❌ Unknown intent action: ${intent.action}")
        }
    }
}
