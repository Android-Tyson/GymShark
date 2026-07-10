package com.historymakers.gymshark.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.AppError.Local.DatabaseError
import com.historymakers.gymshark.core.common.AppError.Local.DiskFull
import com.historymakers.gymshark.core.common.AppError.Local.Unknown
import com.historymakers.gymshark.core.common.AppError.Network
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.domain.repo.ProductRepository
import com.historymakers.gymshark.ui.mapper.toUIModel
import com.historymakers.gymshark.ui.products.model.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductUiState(
    val products: List<ProductUIModel> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

private data class RefreshState(
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val refreshState = MutableStateFlow(RefreshState())

    private var refreshJob: Job? = null
    val uiState = combine(
        productRepository.observeProducts(),
        refreshState
    ) { products, refreshState ->
        ProductUiState(
            products = products.map { it.toUIModel() },
            isInitialLoading = refreshState.isLoading && products.isEmpty(),
            isRefreshing = refreshState.isLoading && products.isNotEmpty(),
            error = refreshState.error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ProductUiState()
    )

    init {
        refreshProducts()
    }

    fun refreshProducts() {
        if (refreshJob?.isActive == true) {
            return
        }
        refreshJob = viewModelScope.launch {
            refreshState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            when (val result = productRepository.refreshProducts()) {
                is DataResult.Success -> {
                    refreshState.update {
                        it.copy(
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is DataResult.Error -> {
                    refreshState.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.toErrorMessage()
                        )
                    }
                }
            }
        }.also { job ->
            job.invokeOnCompletion {
                refreshJob = null
            }
        }
    }

    private fun AppError.toErrorMessage(): String {
        return when (this) {
            Network.NoInternet -> {
                "No internet connection. Showing cached products if available"
            }

            Network.Timeout -> {
                "Request timed out. Showing cached products if available"
            }

            Network.Serialization -> {
                "Error serializing response. Showing cached products if available"
            }

            Network.Unknown -> {
                "Something went wrong. Please try again later"
            }

            is Network.Http -> {
                "Server error. Please try again later"
            }

            Network.IOException -> {
                "Something went wrong. Please try again later"
            }

            DatabaseError -> {
                "Couldn't load saved products"
            }

            DiskFull -> {
                "Not enough space to save products"
            }

            Unknown -> {
                "Something went wrong. Please try again later"
            }
        }
    }

}