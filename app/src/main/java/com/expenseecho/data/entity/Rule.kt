package com.expenseecho.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rules",
    indices = [Index("categoryId"), Index("keyword")],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Rule(
    @PrimaryKey 
    val id: String,            // "kfc"
    val keyword: String,       // lowercased keyword
    val categoryId: String,    // e.g. "food"
    val priority: Int = 0,     // higher priority rules are checked first
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
