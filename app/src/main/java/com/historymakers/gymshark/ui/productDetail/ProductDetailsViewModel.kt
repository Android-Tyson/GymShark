package com.historymakers.gymshark.ui.productDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.historymakers.gymshark.domain.repo.ProductRepository
import com.historymakers.gymshark.ui.mapper.toUIModel
import com.historymakers.gymshark.ui.products.model.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProductDetailsUiState(
    val product: ProductUIModel? = null,
    val productNotFound: Boolean = false,
)

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    productRepository: ProductRepository
) : ViewModel(){

    private val productId = savedStateHandle
        .get<String>("productId")
        ?.toLongOrNull()
        ?: -1L

    val uiState = productRepository
        .observeProduct(productId)
        .map { product ->
            ProductDetailsUiState(
                product = product?.toUIModel(),
                productNotFound = product == null
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductDetailsUiState()
        )

}