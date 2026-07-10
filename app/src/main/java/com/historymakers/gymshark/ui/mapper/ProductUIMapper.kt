package com.historymakers.gymshark.ui.mapper

import androidx.core.text.HtmlCompat
import com.historymakers.gymshark.domain.model.Product
import com.historymakers.gymshark.ui.products.model.ProductUIModel
import java.text.NumberFormat
import java.util.Locale

fun Product.toUIModel(): ProductUIModel {
    return ProductUIModel(
        id = id.toString(),
        title = title,
        imageUrl = imageUrl,
        description = description.fromHtmlToPlainText(),
        priceText = priceInPence.toGBPPrice(),
        heroImageUrls = heroImageUrls.ifEmpty {
            listOfNotNull(imageUrl.takeIf { it.isNotBlank() })
        },
        labels = labels,
        sku = sku,
        handle = handle,
        colour = colour,
        inStock = inStock,
        type = type,
        gender = gender,
        sizeInStock = sizeInStock,
        availableSizes = availableSizes
    )
}

private fun String.fromHtmlToPlainText(): String {
    return HtmlCompat.fromHtml(
        this, HtmlCompat.FROM_HTML_MODE_COMPACT
    ).toString().trim()
}

private fun Int.toGBPPrice(): String {
    return NumberFormat
        .getCurrencyInstance(Locale.UK)
        .format(this / 100.0)
}