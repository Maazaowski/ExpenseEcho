package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "transactions",
    indices = [
        Index("accountId"),
        Index("date"),
        Index("categoryId")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Transaction(
    @PrimaryKey 
    val id: String,            // uuid
    val date: LocalDate,
    val amount: Long,          // store in paisa (PKR * 100)
    val type: String,          // "Income" | "Expense" | "Transfer"
    val categoryId: String?,   // nullable for "-" or "Transfer"
    val accountId: String,     // FK -> Account
    val paymentMethod: String, // "Debit Card" | "Bank Transfer"
    val merchant: String?,
    val description: String?,
    val reference: String?,
    val source: String,        // "sms" | "manual"
    val rawText: String?,      // raw SMS text (optional)
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
