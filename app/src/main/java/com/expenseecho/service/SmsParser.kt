package com.expenseecho.service

import com.expenseecho.data.entity.Transaction
import java.time.LocalDate
import java.util.UUID

object SmsParser {
    private val spaceFix = Regex("[\\u00A0\\u202F]") // non-breaking spaces

    // Updated amount patterns for your bank's specific format
    private val amountRxs = listOf(
        Regex("PKR\\s+([0-9][0-9,]*)(?:\\.([0-9]{1,2}))?", RegexOption.IGNORE_CASE),
        Regex("(?:PKR|Rs\\.?)\\s*([0-9][0-9,]*)(?:\\.([0-9]{1,2}))?", RegexOption.IGNORE_CASE),
        Regex("([0-9][0-9,]*)(?:\\.([0-9]{1,2}))?\\s*(?:PKR|Rs\\.?)", RegexOption.IGNORE_CASE)
    )
    
    // Updated patterns for your bank's SMS format
    private val txIdRx = Regex("Tx ID\\s+([A-Z0-9]+)", RegexOption.IGNORE_CASE)
    private val refRx = Regex("Ref#\\s*([A-Z0-9]+)", RegexOption.IGNORE_CASE)
    
    // Transaction type detection patterns
    private val chargedRx = Regex("is charged", RegexOption.IGNORE_CASE)
    private val receivedRx = Regex("recieved|received", RegexOption.IGNORE_CASE)
    private val sentRx = Regex("sent to", RegexOption.IGNORE_CASE)
    private val fundsTransferRx = Regex("Funds Trsfr|Funds Transfer", RegexOption.IGNORE_CASE)
    
    // Account patterns for your bank format
    private val accountRx = Regex("A/C[:\\s]*([*0-9\\-]+)", RegexOption.IGNORE_CASE)
    private val accountRx2 = Regex("BAF A/C\\s+([*0-9]+)", RegexOption.IGNORE_CASE)
    
    // Merchant/location patterns for your bank
    private val merchantAtRx = Regex("at\\s+([A-Z0-9 &\\-_.]{3,60})(?:\\.|$)", RegexOption.IGNORE_CASE)
    private val fromPersonRx = Regex("from\\s+([A-Z\\s]{3,40})\\s+(?:BAF|TMB)", RegexOption.IGNORE_CASE)
    private val toPersonRx = Regex("to\\s+([A-Z\\s]{3,40})\\s+(?:BAF|TMB)", RegexOption.IGNORE_CASE)
    
    // Enhanced patterns for better merchant extraction
    private val merchantAtRx2 = Regex("charged for.*?at\\s+([A-Z0-9 &\\-_.,']{3,60})", RegexOption.IGNORE_CASE)
    private val viaRx = Regex("via\\s+([A-Z0-9 \\-_.,']{3,40})", RegexOption.IGNORE_CASE)
    
    // Date extraction pattern
    private val dateRx = Regex("on\\s+(\\d{1,2})-([A-Z]{3})-(\\d{2})", RegexOption.IGNORE_CASE)
    private val timeRx = Regex("at\\s+(\\d{1,2}):(\\d{2}):(\\d{2})", RegexOption.IGNORE_CASE)

    data class ParsedSms(
        val date: LocalDate = LocalDate.now(),
        val amount: Long,
        val type: String,
        val paymentMethod: String,
        val categoryId: String?,     // null for "-" or Transfer
        val accountMask: String?,
        val merchant: String?,
        val description: String?,
        val reference: String?,
        val raw: String
    ) {
        fun toTransaction(accountId: String = "spend-0030-1"): Transaction {
            return Transaction(
                id = UUID.randomUUID().toString(),
                date = date,
                amount = amount,
                type = type,
                categoryId = categoryId,
                accountId = accountId,
                paymentMethod = paymentMethod,
                merchant = merchant,
                description = description ?: merchant ?: "Bank Transaction",
                reference = reference,
                source = "sms",
                rawText = raw
            )
        }
    }

    fun parse(rawInput: String): ParsedSms? {
        val raw = rawInput.replace(spaceFix, " ").trim()
        
        // Extract amount and convert to paisa
        val amount = extractAmount(raw) ?: return null
        
        // Determine transaction type based on your bank's specific language
        val isCharged = chargedRx.containsMatchIn(raw)
        val isReceived = receivedRx.containsMatchIn(raw)
        val isSent = sentRx.containsMatchIn(raw)
        val isFundsTransfer = fundsTransferRx.containsMatchIn(raw)
        
        val type = when {
            isReceived -> "Income"
            isSent || (isFundsTransfer && !isReceived) -> "Transfer"
            isCharged -> "Expense"
            else -> "Expense" // default to expense if unclear
        }
        
        // Determine payment method
        val paymentMethod = when {
            isReceived || isSent || isFundsTransfer -> "Bank Transfer"
            isCharged -> "Debit Card"
            else -> "Debit Card"
        }
        
        // Extract account information (try both patterns)
        val accountMask = accountRx.find(raw)?.groupValues?.get(1) 
            ?: accountRx2.find(raw)?.groupValues?.get(1)
        
        // Extract transaction ID or reference
        val reference = txIdRx.find(raw)?.groupValues?.get(1) 
            ?: refRx.find(raw)?.groupValues?.get(1)
        
        // Extract merchant/person based on transaction type
        val merchant = when {
            isCharged -> extractMerchantFromCharged(raw)
            isReceived -> extractPersonFrom(raw)
            isSent -> extractPersonTo(raw)
            else -> null
        }
        
        // Try alternative extraction methods if primary failed
        val finalMerchant = merchant ?: when {
            isCharged -> extractMerchantAlternative(raw)
            isFundsTransfer -> extractViaMethod(raw)
            else -> null
        }
        
        // Determine category heuristics (will be refined later by rules)
        val categoryId = when {
            isSent || (isFundsTransfer && !isReceived) -> null // will be categorized as "transfer"
            isReceived -> null // income, no category needed
            else -> null // will be auto-categorized by rules
        }
        
        // Create description
        val description = when {
            reference != null && finalMerchant != null -> "$finalMerchant (Tx: $reference)"
            reference != null -> "Tx ID: $reference"
            finalMerchant != null -> finalMerchant
            else -> "Bank Transaction"
        }
        
        return ParsedSms(
            amount = amount,
            type = type,
            paymentMethod = paymentMethod,
            categoryId = categoryId,
            accountMask = accountMask,
            merchant = finalMerchant,
            description = description,
            reference = reference,
            raw = raw
        )
    }
    
    private fun extractAmount(text: String): Long? {
        for (regex in amountRxs) {
            val match = regex.find(text)
            if (match != null) {
                try {
                    val intPart = match.groupValues[1].replace(",", "")
                    val decPart = match.groupValues.getOrNull(2).orEmpty()
                        .padEnd(2, '0').take(2)
                    
                    val amountString = intPart + decPart
                    return amountString.toLongOrNull()
                } catch (e: Exception) {
                    continue
                }
            }
        }
        return null
    }
    
    /**
     * Extract merchant from "charged" transaction (debit card purchases)
     * Example: "at FOOD PANDA KARACHI PK."
     */
    private fun extractMerchantFromCharged(text: String): String? {
        val match = merchantAtRx.find(text)
        return match?.groupValues?.get(1)?.trim()?.removeSuffix(".")
    }
    
    /**
     * Extract person name from "received" transaction
     * Example: "from SYED MUHAMMAD MAAZ BAF"
     */
    private fun extractPersonFrom(text: String): String? {
        val match = fromPersonRx.find(text)
        return match?.groupValues?.get(1)?.trim()
    }
    
    /**
     * Extract person name from "sent" transaction  
     * Example: "to MUHAMMAD ZAHID TMB"
     */
    private fun extractPersonTo(text: String): String? {
        val match = toPersonRx.find(text)
        return match?.groupValues?.get(1)?.trim()
    }
    
    /**
     * Alternative merchant extraction for charged transactions
     * Example: "charged for PKR 2,853.99 on 1-Sep-25 at 01:13:03 at FOOD PANDA KARACHI PK"
     */
    private fun extractMerchantAlternative(text: String): String? {
        val match = merchantAtRx2.find(text)
        return match?.groupValues?.get(1)?.trim()?.removeSuffix(".")
    }
    
    /**
     * Extract method/channel from "via" transactions
     * Example: "via Funds Trsfr Web"
     */
    private fun extractViaMethod(text: String): String? {
        val match = viaRx.find(text)
        return match?.groupValues?.get(1)?.trim()
    }
    
    /**
     * Test method to validate SMS parsing with your bank's actual formats
     */
    fun testParse() {
        val testCases = listOf(
            // Debit card charge
            """Dear Customer,
Your Debit Card against A/C: 0030-1***6809 is charged for PKR 2,853.99 on 1-Sep-25 at 01:13:03 at FOOD PANDA KARACHI PK.
Bal: Reply with AB
021111225111""",
            
            // Money received
            "PKR 1,000.00 recieved from SYED MUHAMMAD MAAZ BAF in your BAF A/C **6809 on 30-Aug-25 01:21:33 via Funds Trsfr Tx ID FT25242030CM60L6",
            
            // Money sent
            """PKR 450.00 sent to MUHAMMAD ZAHID TMB from your BAF A/C **6809 on 28-Aug-25 10:54:56 via Funds Trsfr Web Tx ID FT252400K1DZSJ47
Bal: Reply with AB
021111225111"""
        )
        
        testCases.forEach { sms ->
            val result = parse(sms)
            println("SMS: $sms")
            println("Parsed: $result")
            println("Account Match: ${result?.accountMask?.contains("6809")}")
            println("---")
        }
    }
}
