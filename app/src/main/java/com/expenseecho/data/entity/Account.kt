package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey 
    val id: String,            // "spend-0030-1"
    val displayName: String,   // "Spending (0030-1)"
    val mask: String,          // "****0030-1******6809"
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
