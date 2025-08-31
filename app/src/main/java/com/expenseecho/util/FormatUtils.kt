package com.expenseecho.util

import java.text.NumberFormat
import java.util.Locale

/**
 * Format currency amount in paisa to PKR string
 */
fun formatCurrency(amountInPaisa: Long): String {
    val amountInPKR = amountInPaisa / 100.0
    val formatter = NumberFormat.getCurrencyInstance(Locale("ur", "PK"))
    formatter.currency = java.util.Currency.getInstance("PKR")
    return formatter.format(amountInPKR).replace("PKR", "Rs")
}

/**
 * Format percentage
 */
fun formatPercentage(value: Float): String {
    return String.format("%.1f%%", value)
}

/**
 * Format large numbers with abbreviations (K, M, B)
 */
fun formatLargeNumber(number: Long): String {
    return when {
        number >= 1_000_000_000 -> String.format("%.1fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format("%.1fK", number / 1_000.0)
        else -> number.toString()
    }
}
