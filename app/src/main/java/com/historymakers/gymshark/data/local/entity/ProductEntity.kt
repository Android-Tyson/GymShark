package com.historymakers.gymshark.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Long = 0,
    val position: Int,
    val title: String,
    val sku: String,
    val handle: String,
    val colour: String,
    val price: Int,
    val inStock: Boolean,
    val imageUrl: String,
    val heroImageUrls: List<String>,
    val type: String,
    val labels: List<String>,
    val gender: List<String>,
    val sizeInStock: List<String>,
    val availableSizes: List<String>,
    val description: String,
)
