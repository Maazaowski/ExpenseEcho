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
        Log.d(TAG, "SMS received: ${intent.action}")
        
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            
            for (smsMessage in smsMessages) {
                val sender = smsMessage.originatingAddress
                val body = smsMessage.messageBody
                val timestamp = smsMessage.timestampMillis
                
                Log.d(TAG, "SMS from: $sender, Body: $body")
                
                // Check if SMS is from bank
                if (sender == BANK_SMS_NUMBER || sender?.contains("8287") == true) {
                    Log.d(TAG, "Processing bank SMS")
                    
                    // Check if it contains bank transaction keywords
                    if (body.contains("Dear Customer", ignoreCase = true) || 
                        body.contains("BAF A/C", ignoreCase = true) ||
                        body.contains("Debit Card", ignoreCase = true) ||
                        body.contains("PKR", ignoreCase = true)) {
                        
                        // Process the SMS
                        smsReaderService.processSingleSms(body)
                    }
                }
            }
        }
    }
}
