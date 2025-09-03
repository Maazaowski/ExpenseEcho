package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey 
    val id: String,            // "spend-0030-1"
    val displayName: String,   // "Spending (6809)"
    val mask: String,          // "****0030-1******6809"
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    /**
     * Extract the last 4 digits from the account mask
     * Example: "****0030-1******6809" -> "6809"
     */
    fun getLastFourDigits(): String {
        return mask.takeLast(4)
    }
    
    /**
     * Get display name with last 4 digits
     * Example: "Spending (6809)"
     */
    fun getDisplayNameWithLastFour(): String {
        val accountType = displayName.substringBefore("(").trim()
        return "$accountType (${getLastFourDigits()})"
    }
}
