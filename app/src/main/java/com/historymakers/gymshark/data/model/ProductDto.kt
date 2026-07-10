package com.historymakers.gymshark.data.model

data class ProductDto(
    val id: Long?,
    val sku: String?,
    val inStock: Boolean?,
    val sizeInStock: List<String>?,
    val availableSizes: List<AvailableSizeDto>?,
    val handle: String?,
    val title: String?,
    val description: String?,
    val type: String?,
    val gender: List<String>?,
    val fit: String?,
    val labels: List<String>?,
    val colour: String?,
    val price: Int?,
    val compareAtPrice: Int?,
    val discountPercentage: Int?,
    val featuredMedia: MediaDto?,
    val media: List<MediaDto>?,
    val objectID: String?
)
