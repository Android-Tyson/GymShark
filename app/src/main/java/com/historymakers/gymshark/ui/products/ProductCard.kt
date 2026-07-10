package com.historymakers.gymshark.ui.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.historymakers.gymshark.R
import com.historymakers.gymshark.ui.products.model.ProductUIModel

private const val PRODUCT_CARD_TAG = "ProductCard"

@Composable
fun ProductCard(
    product: ProductUIModel,
    onProductClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = { onProductClick(product.id) }
    ) {
        Column {
            ProductImage(
                product = product,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
            ) {
                Text(
                    text = product.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = product.colour,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = product.priceText,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun ProductImage(
    product: ProductUIModel,
    modifier: Modifier = Modifier
) {
    if (product.imageUrl.isBlank()) {
        ProductImageState(
            message = "No image URL",
            modifier = modifier
        )
        return
    }

    Box {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.gymshark_logo),
            fallback = painterResource(R.drawable.gymshark_logo),
        )
        if (product.labels.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = product.labels[0],
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(vertical = 8.dp, horizontal = 16.dp)

                )
            }
        }
    }
}

@Composable
private fun ProductImageState(
    modifier: Modifier = Modifier,
    message: String? = null,
    showProgress: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        when {
            showProgress -> CircularProgressIndicator()
            message != null ->
                Image(
                    painter = painterResource(R.drawable.gymshark_logo),
                    contentDescription = message,
                    contentScale = ContentScale.Crop
                )
        }
    }
}

@Composable
@Preview
fun ProductCardPreview() {
    MaterialTheme() {
        ProductCard(
            product = ProductUIModel(
                id = "111",
                title = "Product 1",
                imageUrl = "https://cdn.shopify.com/s/files/1/1326/4923/products/SpeedLEGGINGNavy-B3A3E-UBCY.A-Edit_BK.jpg?v=1649254794",
                description = "This is a product description",
                priceText = "10",
                sku = "BEBE",
                handle = "gymshark-legging-navy-ss2",
                colour = "Navy",
                inStock = true,
                heroImageUrls = listOf(""),
                labels = listOf("new", "trending"),
                type = "Leggings",
                gender = listOf("f"),
                sizeInStock = listOf("xs"),
                availableSizes = listOf("xs", "s", "m", "l", "xl"),
            )
        )
    }
}
