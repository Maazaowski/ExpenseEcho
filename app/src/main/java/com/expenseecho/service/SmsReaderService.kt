package com.expenseecho.service

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.expenseecho.data.repository.TransactionRepository
import com.expenseecho.data.repository.AccountRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsReaderService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    companion object {
        private const val TAG = "SmsReaderService"
        private const val BANK_SMS_NUMBER = "8287" // Your bank's SMS number
        private const val TARGET_ACCOUNT_MASK = "6809" // Last 4 digits of target account
    }
    
    /**
     * Import all SMS messages from the bank number
     */
    fun importAllBankSms(onProgress: (Int, Int) -> Unit = { _, _ -> }, onComplete: (Int) -> Unit = {}) {
        serviceScope.launch {
            try {
                val smsMessages = getAllBankSms()
                var processedCount = 0
                var successCount = 0
                
                Log.d(TAG, "Found ${smsMessages.size} SMS messages from bank")
                
                smsMessages.forEach { sms ->
                    processedCount++
                    onProgress(processedCount, smsMessages.size)
                    
                                         try {
                         Log.d(TAG, "Processing SMS: ${sms.body}")
                         val parsedSms = SmsParser.parse(sms.body)
                         Log.d(TAG, "Parsed SMS: $parsedSms")
                         
                         if (parsedSms != null) {
                             Log.d(TAG, "Account mask from SMS: ${parsedSms.accountMask}")
                             if (parsedSms.accountMask?.contains(TARGET_ACCOUNT_MASK) == true) {
                                 // Find the account or create it if it doesn't exist
                                 var account = accountRepository.getAccountByMask(parsedSms.accountMask)
                                 if (account == null) {
                                     // Create default account if it doesn't exist
                                     val defaultAccount = com.expenseecho.data.entity.Account(
                                         id = "spend-0030-1",
                                         displayName = "Spending (6809)",
                                         mask = parsedSms.accountMask ?: "****0030-1******6809"
                                     )
                                     accountRepository.insertAccount(defaultAccount)
                                     account = defaultAccount
                                     Log.d(TAG, "Created default account: ${account.id}")
                                 }
                                 
                                 val transaction = parsedSms.toTransaction(account.id)
                                 Log.d(TAG, "Created transaction: ID=${transaction.id}, Amount=${transaction.amount}, Date=${transaction.date}, Type=${transaction.type}, AccountId=${transaction.accountId}")
                                 
                                 transactionRepository.insertTransaction(transaction)
                                 successCount++
                                 
                                 Log.d(TAG, "Successfully inserted transaction: ${transaction.description} - ${transaction.amount}")
                             } else {
                                 Log.d(TAG, "Account mask doesn't match target: ${parsedSms.accountMask} vs $TARGET_ACCOUNT_MASK")
                             }
                         } else {
                             Log.d(TAG, "Failed to parse SMS")
                         }
                     } catch (e: Exception) {
                         Log.e(TAG, "Error processing SMS: ${e.message}", e)
                     }
                }
                
                Log.d(TAG, "SMS import completed: $successCount transactions imported")
                onComplete(successCount)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error importing SMS messages: ${e.message}", e)
                onComplete(0)
            }
        }
    }
    
    /**
     * Import recent SMS messages (last 30 days)
     */
    fun importRecentBankSms(days: Int = 30, onComplete: (Int) -> Unit = {}) {
        serviceScope.launch {
            try {
                val cutoffTime = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
                val smsMessages = getAllBankSms(sinceTimestamp = cutoffTime)
                var successCount = 0
                
                Log.d(TAG, "Found ${smsMessages.size} recent SMS messages from bank")
                
                                smsMessages.forEach { sms ->
                     try {
                         Log.d(TAG, "Processing recent SMS: ${sms.body}")
                         val parsedSms = SmsParser.parse(sms.body)
                         Log.d(TAG, "Parsed recent SMS: $parsedSms")
                         
                         if (parsedSms != null) {
                             Log.d(TAG, "Account mask from recent SMS: ${parsedSms.accountMask}")
                             if (parsedSms.accountMask?.contains(TARGET_ACCOUNT_MASK) == true) {
                                 // Find the account or create it if it doesn't exist
                                 var account = accountRepository.getAccountByMask(parsedSms.accountMask)
                                 if (account == null) {
                                     // Create default account if it doesn't exist
                                     val defaultAccount = com.expenseecho.data.entity.Account(
                                         id = "spend-0030-1",
                                         displayName = "Spending (6809)",
                                         mask = parsedSms.accountMask ?: "****0030-1******6809"
                                     )
                                     accountRepository.insertAccount(defaultAccount)
                                     account = defaultAccount
                                     Log.d(TAG, "Created default account for recent SMS: ${account.id}")
                                 }
                                 
                                 val transaction = parsedSms.toTransaction(account.id)
                                 Log.d(TAG, "Created recent transaction: ID=${transaction.id}, Amount=${transaction.amount}, Date=${transaction.date}, AccountId=${transaction.accountId}")
                                 
                                 transactionRepository.insertTransaction(transaction)
                                 successCount++
                                 
                                 Log.d(TAG, "Successfully inserted recent transaction: ${transaction.description}")
                             } else {
                                 Log.d(TAG, "Recent SMS account mask doesn't match: ${parsedSms.accountMask} vs $TARGET_ACCOUNT_MASK")
                             }
                         } else {
                             Log.d(TAG, "Failed to parse recent SMS")
                         }
                     } catch (e: Exception) {
                         Log.e(TAG, "Error processing recent SMS: ${e.message}", e)
                     }
                 }
                
                Log.d(TAG, "Recent SMS import completed: $successCount transactions imported")
                onComplete(successCount)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error importing recent SMS messages: ${e.message}", e)
                onComplete(0)
            }
        }
    }
    
    /**
     * Process a single new SMS message
     */
    fun processSingleSms(smsBody: String, timestamp: Long = System.currentTimeMillis()) {
        serviceScope.launch {
            try {
                val parsedSms = SmsParser.parse(smsBody)
                if (parsedSms != null && parsedSms.accountMask?.contains(TARGET_ACCOUNT_MASK) == true) {
                    // Find the account or create it if it doesn't exist
                    var account = accountRepository.getAccountByMask(parsedSms.accountMask)
                    if (account == null) {
                        // Create default account if it doesn't exist
                        val defaultAccount = com.expenseecho.data.entity.Account(
                            id = "spend-0030-1",
                            displayName = "Spending (6809)",
                            mask = parsedSms.accountMask ?: "****0030-1******6809"
                        )
                        accountRepository.insertAccount(defaultAccount)
                        account = defaultAccount
                        Log.d(TAG, "Created default account for single SMS: ${account.id}")
                    }
                    
                    val transaction = parsedSms.toTransaction(account.id)
                    transactionRepository.insertTransaction(transaction)
                    
                    Log.d(TAG, "Processed new SMS transaction: ${transaction.description}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing single SMS: ${e.message}", e)
            }
        }
    }
    
    private fun getAllBankSms(sinceTimestamp: Long? = null): List<SmsMessage> {
        val smsMessages = mutableListOf<SmsMessage>()
        
        try {
            val uri = Telephony.Sms.CONTENT_URI
            val projection = arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.TYPE
            )
            
            var selection = "${Telephony.Sms.ADDRESS} = ? AND ${Telephony.Sms.TYPE} = ?"
            var selectionArgs = arrayOf(BANK_SMS_NUMBER, Telephony.Sms.MESSAGE_TYPE_INBOX.toString())
            
            // Add timestamp filter if provided
            if (sinceTimestamp != null) {
                selection += " AND ${Telephony.Sms.DATE} >= ?"
                selectionArgs = arrayOf(BANK_SMS_NUMBER, Telephony.Sms.MESSAGE_TYPE_INBOX.toString(), sinceTimestamp.toString())
            }
            
            val sortOrder = "${Telephony.Sms.DATE} DESC"
            
            val cursor: Cursor? = context.contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            
            cursor?.use {
                val addressIndex = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
                val bodyIndex = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
                val dateIndex = it.getColumnIndexOrThrow(Telephony.Sms.DATE)
                
                while (it.moveToNext()) {
                    val address = it.getString(addressIndex)
                    val body = it.getString(bodyIndex)
                    val date = it.getLong(dateIndex)
                    
                    // Additional filtering for bank SMS content
                    if (body.contains("Dear Customer", ignoreCase = true) || 
                        body.contains("BAF A/C", ignoreCase = true) ||
                        body.contains("Debit Card", ignoreCase = true) ||
                        body.contains("PKR", ignoreCase = true)) {
                        
                        smsMessages.add(SmsMessage(address, body, date))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error reading SMS messages: ${e.message}", e)
        }
        
        return smsMessages
    }
    
    data class SmsMessage(
        val address: String,
        val body: String,
        val timestamp: Long
    ) {
        val date: LocalDate
            get() = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
    }
}
