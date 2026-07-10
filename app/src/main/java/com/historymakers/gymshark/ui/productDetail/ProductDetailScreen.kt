package com.historymakers.gymshark.ui.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.historymakers.gymshark.R
import com.historymakers.gymshark.ui.products.model.ProductUIModel

@Composable
fun ProductDetailRoute(
    viewModel: ProductDetailsViewModel = hiltViewModel(), onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailsScreen(
        uiState = uiState, onBackClick = onBackClick
    )
}

@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsUiState, onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ProductDetailsTopBar(
                title = uiState.product?.title ?: "", onBackClick = onBackClick
            )
        }) { paddingValues ->
        when {
            uiState.product != null -> {
                ProductDetailsContent(
                    product = uiState.product,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            uiState.productNotFound -> {
                ProductNotFoundState(
                    modifier = Modifier.padding(paddingValues),
                    onBackClick = onBackClick
                )
            }

            else -> {
                ProductDetailsLoadingState(modifier = Modifier.padding(paddingValues))
            }
        }

    }

}

@Composable
fun ProductNotFoundState(modifier: Modifier, onBackClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Product not found",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = onBackClick
            ) {
                Text("Back To Products")
            }
        }
    }
}

@Composable
fun ProductDetailsLoadingState(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

}

@Composable
fun ProductDetailsContent(
    product: ProductUIModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProductImageCarousel(
            product = product,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = product.priceText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProductImageCarousel(product: ProductUIModel) {
    val imageUrls = product.heroImageUrls.ifEmpty {
        listOf(product.imageUrl)
    }
    val pagerState = rememberPagerState(
        pageCount = { imageUrls.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = imageUrls[page],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.gymshark_logo)
            )
        }
        if (imageUrls.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(imageUrls.size) { index ->
                    val isSelected = pagerState.currentPage == index

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (isSelected) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) {
                                    MaterialTheme.colorScheme.surface
                                } else {
                                    MaterialTheme.colorScheme.outline
                                }
                            )
                    )
                    if (index != imageUrls.lastIndex) {
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                }
            }
        }


    }
}

@Composable
fun ProductDetailsTopBar(
    title: String, onBackClick: () -> Unit
) {
    Surface(
        modifier = Modifier.statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}