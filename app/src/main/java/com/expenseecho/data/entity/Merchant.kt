package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "merchants",
    indices = [
        Index("name", unique = true),
        Index("categoryId")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Merchant(
    @PrimaryKey 
    val id: String,                    // UUID
    val name: String,                  // Merchant name from SMS (e.g., "FOOD PANDA KARACHI PK")
    val normalizedName: String,        // Normalized name for matching (e.g., "food panda")
    val categoryId: String? = null,    // Mapped category ID (null = uncategorized)
    val transactionCount: Int = 0,     // Number of transactions from this merchant
    val lastSeenAt: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Normalize merchant name for better matching
     * Removes common suffixes, converts to lowercase, trims whitespace
     */
    companion object {
        fun normalizeName(merchantName: String): String {
            return merchantName
                .lowercase()
                .replace(Regex("\\s+(pk|pakistan|karachi|lahore|islamabad)\\s*$"), "")
                .replace(Regex("\\s+(ltd|limited|inc|corp|corporation)\\s*$"), "")
                .trim()
        }
        
        fun createFromName(merchantName: String): Merchant {
            return Merchant(
                id = java.util.UUID.randomUUID().toString(),
                name = merchantName.trim(),
                normalizedName = normalizeName(merchantName)
            )
        }
    }
}
