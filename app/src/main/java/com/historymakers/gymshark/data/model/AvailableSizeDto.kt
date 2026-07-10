package com.historymakers.gymshark.data.model

data class AvailableSizeDto(
    val id: Long,
    val inStock: Boolean?,
    val inventoryQuantity: Int?,
    val price: Int?,
    val size: String?,
    val sku: String?
)