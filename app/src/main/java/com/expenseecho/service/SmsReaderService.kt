package com.expenseecho.service

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.expenseecho.data.repository.AccountRepository
import com.expenseecho.data.repository.TransactionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsReaderService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val processingMutex = Mutex()
    
    companion object {
        private const val TAG = "SmsReaderService"
        private const val BANK_SMS_NUMBER = "8287"
        private val TARGET_ACCOUNT_PATTERNS = arrayOf("0030-1", "6809")
    }
    
    data class SmsMessage(
        val body: String,
        val timestamp: Long,
        val address: String
    )
    
    private fun isTargetAccount(accountMask: String?): Boolean {
        return accountMask != null && TARGET_ACCOUNT_PATTERNS.any { pattern ->
            accountMask.contains(pattern, ignoreCase = true)
        }
    }
    
    /**
     * Import all SMS messages from the bank
     */
    fun importAllBankSms(onComplete: (Int) -> Unit = {}) {
        serviceScope.launch {
            try {
                val smsMessages = getAllBankSms()
                var successCount = 0
                var processedCount = 0
                
                Log.i(TAG, "Starting SMS import: Found ${smsMessages.size} SMS messages from bank")
                
                var parseFailCount = 0
                var accountMismatchCount = 0
                
                smsMessages.forEach { sms ->
                    try {
                        processedCount++
                        val parsedSms = SmsParser.parse(sms.body)
                        
                        if (parsedSms != null) {
                            val accountMask = parsedSms.accountMask
                            if (accountMask != null && isTargetAccount(accountMask)) {
                                processingMutex.withLock {
                                    var account = accountRepository.getAccountByMask(accountMask)
                                    if (account == null) {
                                        account = com.expenseecho.data.entity.Account(
                                            id = "spend-0030-1",
                                            displayName = "Spending (6809)",
                                            mask = accountMask
                                        )
                                    }
                                    
                                    val transaction = parsedSms.toTransaction(account.id)
                                    transactionRepository.insertTransactionWithAccount(transaction, accountMask)
                                    successCount++
                                    
                                    if (processedCount % 25 == 0) {
                                        Log.d(TAG, "Progress: $processedCount/${smsMessages.size} SMS processed, $successCount transactions imported")
                                    }
                                }
                            } else {
                                accountMismatchCount++
                                if (accountMismatchCount <= 3) {
                                    Log.d(TAG, "Account mismatch #$accountMismatchCount: '$accountMask' doesn't match patterns: ${TARGET_ACCOUNT_PATTERNS.joinToString(", ")}")
                                }
                            }
                        } else {
                            parseFailCount++
                            if (parseFailCount <= 3) {
                                Log.d(TAG, "Parse fail #$parseFailCount: SMS body: ${sms.body.take(100)}")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing SMS #$processedCount: ${e.message}", e)
                    }
                }
                
                Log.i(TAG, "SMS import completed: $successCount/$processedCount transactions imported")
                Log.i(TAG, "  - Parse failures: $parseFailCount")
                Log.i(TAG, "  - Account mismatches: $accountMismatchCount")
                Log.i(TAG, "  - Successfully imported: $successCount")
                onComplete(successCount)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error importing SMS messages: ${e.message}", e)
                onComplete(0)
            }
        }
    }
    
    /**
     * Import recent SMS messages (optimized version)
     */
    fun importRecentBankSms(days: Int = 30, onComplete: (Int) -> Unit = {}) {
        serviceScope.launch {
            try {
                val cutoffTime = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
                val smsMessages = getAllBankSms(sinceTimestamp = cutoffTime)
                var successCount = 0
                var processedCount = 0
                
                Log.i(TAG, "Starting recent SMS import: Found ${smsMessages.size} SMS messages from bank (last $days days)")
                
                var parseFailCount = 0
                var accountMismatchCount = 0
                
                smsMessages.forEach { sms ->
                    try {
                        processedCount++
                        val parsedSms = SmsParser.parse(sms.body)
                        
                        if (parsedSms != null) {
                            val accountMask = parsedSms.accountMask
                            if (accountMask != null && isTargetAccount(accountMask)) {
                                processingMutex.withLock {
                                    var account = accountRepository.getAccountByMask(accountMask)
                                    if (account == null) {
                                        account = com.expenseecho.data.entity.Account(
                                            id = "spend-0030-1",
                                            displayName = "Spending (6809)",
                                            mask = accountMask
                                        )
                                    }
                                    
                                    val transaction = parsedSms.toTransaction(account.id)
                                    transactionRepository.insertTransactionWithAccount(transaction, accountMask)
                                    successCount++
                                    
                                    if (processedCount % 10 == 0) {
                                        Log.d(TAG, "Progress: $processedCount/${smsMessages.size} SMS processed, $successCount transactions imported")
                                    }
                                }
                            } else {
                                accountMismatchCount++
                                if (accountMismatchCount <= 3) {
                                    Log.d(TAG, "RECENT Account mismatch #$accountMismatchCount: '$accountMask' doesn't match patterns: ${TARGET_ACCOUNT_PATTERNS.joinToString(", ")}")
                                }
                            }
                        } else {
                            parseFailCount++
                            if (parseFailCount <= 3) {
                                Log.d(TAG, "RECENT Parse fail #$parseFailCount: SMS body: ${sms.body.take(100)}")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing SMS #$processedCount: ${e.message}", e)
                    }
                }
                
                Log.i(TAG, "Recent SMS import completed: $successCount/$processedCount transactions imported")
                Log.i(TAG, "  - Parse failures: $parseFailCount")
                Log.i(TAG, "  - Account mismatches: $accountMismatchCount")
                Log.i(TAG, "  - Successfully imported: $successCount")
                onComplete(successCount)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error importing recent SMS messages: ${e.message}", e)
                onComplete(0)
            }
        }
    }
    
    /**
     * Refresh recent transactions by checking for new SMS messages
     * Only processes SMS from the last 2 days to avoid duplicates
     */
    suspend fun refreshRecentTransactions() {
        try {
            Log.d(TAG, "🔄 Refreshing recent transactions (last 2 days)...")
            importRecentBankSms(days = 2)
        } catch (e: Exception) {
            Log.e(TAG, "💥 Error refreshing recent transactions: ${e.message}", e)
        }
    }
    
    /**
     * Process a single new SMS message (optimized)
     */
    fun processSingleSms(smsBody: String) {
        serviceScope.launch {
            try {
                Log.d(TAG, "🔄 Processing real-time SMS: ${smsBody.take(100)}...")
                val parsedSms = SmsParser.parse(smsBody)
                if (parsedSms != null) {
                    val accountMask = parsedSms.accountMask
                    Log.d(TAG, "📋 Parsed SMS - Type: ${parsedSms.type}, Amount: ${parsedSms.amount}, Account: $accountMask, Date: ${parsedSms.date}")
                    
                    if (accountMask != null && isTargetAccount(accountMask)) {
                        processingMutex.withLock {
                            var account = accountRepository.getAccountByMask(accountMask)
                            if (account == null) {
                                account = com.expenseecho.data.entity.Account(
                                    id = "spend-0030-1",
                                    displayName = "Spending (6809)",
                                    mask = accountMask
                                )
                            }
                            
                            val transaction = parsedSms.toTransaction(account.id)
                            transactionRepository.insertTransactionWithAccount(transaction, accountMask)
                            Log.i(TAG, "✅ Real-time transaction processed: ${transaction.type} - ${transaction.merchant} - ${transaction.amount} - ${transaction.date}")
                        }
                    } else {
                        Log.d(TAG, "❌ SMS not processed: Account mask '$accountMask' doesn't match target patterns")
                    }
                } else {
                    Log.d(TAG, "❌ SMS not processed: Parsing failed")
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Error processing single SMS: ${e.message}", e)
            }
        }
    }
    
    /**
     * Get all SMS messages from the bank number
     */
    private fun getAllBankSms(sinceTimestamp: Long? = null): List<SmsMessage> {
        val smsMessages = mutableListOf<SmsMessage>()
        val contentResolver: ContentResolver = context.contentResolver
        
        var selection = "${Telephony.Sms.ADDRESS} = ?"
        val selectionArgs = mutableListOf(BANK_SMS_NUMBER)
        
        if (sinceTimestamp != null) {
            selection += " AND ${Telephony.Sms.DATE} >= ?"
            selectionArgs.add(sinceTimestamp.toString())
        }
        
        val cursor: Cursor? = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.ADDRESS
            ),
            selection,
            selectionArgs.toTypedArray(),
            "${Telephony.Sms.DATE} ASC"
        )
        
        cursor?.use {
            val bodyIndex = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val dateIndex = it.getColumnIndexOrThrow(Telephony.Sms.DATE)
            val addressIndex = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
            
            while (it.moveToNext()) {
                val body = it.getString(bodyIndex)
                val timestamp = it.getLong(dateIndex)
                val address = it.getString(addressIndex)
                
                smsMessages.add(SmsMessage(body, timestamp, address))
            }
        }
        
        return smsMessages
    }
}
