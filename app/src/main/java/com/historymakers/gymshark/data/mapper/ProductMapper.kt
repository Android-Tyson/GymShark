package com.historymakers.gymshark.data.mapper

import com.historymakers.gymshark.data.local.entity.ProductEntity
import com.historymakers.gymshark.data.model.ProductDto
import com.historymakers.gymshark.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        sku = sku,
        handle = handle,
        colour = colour,
        priceInPence = price,
        inStock = inStock,
        imageUrl = imageUrl,
        heroImageUrls = heroImageUrls,
        type = type,
        labels = labels,
        gender = gender,
        sizeInStock = sizeInStock,
        availableSizes = availableSizes,
        description = description
    )
}

fun ProductDto.toEntity(position: Int): ProductEntity {
    val heroImageUrls = media
        .orEmpty()
        .mapNotNull { it.src?.takeIf(String::isNotBlank) }
    val imageUrl = featuredMedia?.src?.takeIf { it.isNotBlank() }
        ?: media?.firstNotNullOfOrNull { it.src?.takeIf(String::isNotBlank) }
        ?: ""
    return ProductEntity(
        id = id
            ?: objectID?.toLongOrNull()
            ?: throw IllegalArgumentException("Product ID is missing"),
        position = position,
        title = title.orEmpty(),
        sku = sku.orEmpty(),
        handle = handle.orEmpty(),
        colour = colour.orEmpty(),
        price = price ?: 0,
        inStock = inStock ?: false,
        imageUrl = imageUrl,
        heroImageUrls = heroImageUrls,
        type = type.orEmpty(),
        labels = labels ?: emptyList(),
        gender = gender ?: emptyList(),
        sizeInStock = sizeInStock ?: emptyList(),
        availableSizes = availableSizes
            .orEmpty()
            .filter { it.inStock == true }
            .mapNotNull { it.size }
            .filter { it.isNotBlank() },
        description = description.orEmpty()
    )
}