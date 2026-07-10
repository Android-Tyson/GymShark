package com.historymakers.gymshark.ui.products.model

data class ProductUIModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val priceText: String,
    val labels: List<String>,
    val heroImageUrls: List<String>,
    val sku: String,
    val handle: String,
    val colour: String,
    val inStock: Boolean,
    val type: String,
    val gender: List<String>,
    val sizeInStock: List<String>,
    val availableSizes: List<String>,
)
