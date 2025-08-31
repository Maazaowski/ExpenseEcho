package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey 
    val id: String,            // "food", "groceries", "bills"
    val name: String,
    val isBudgetable: Boolean = true,  // false for "-" or "Transfer"
    val color: String = "#6200EE",     // hex color for UI
    val icon: String = "category",      // icon name
    val createdAt: Long = System.currentTimeMillis()
)
