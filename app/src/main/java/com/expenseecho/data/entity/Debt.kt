package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class Debt(
    @PrimaryKey 
    val id: String,            // "card1"
    val name: String,
    val balance: Long,         // current balance in paisa
    val apr: Double,           // annual percentage rate
    val minPayment: Long,      // minimum monthly payment in paisa
    val startingBalance: Long? = null,  // original balance when added
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
