package com.historymakers.gymshark.data.repo

import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.data.datasource.LocalDataSource
import com.historymakers.gymshark.data.datasource.RemoteDataSource
import com.historymakers.gymshark.data.local.entity.ProductEntity
import com.historymakers.gymshark.data.model.ProductDto
import com.historymakers.gymshark.data.model.ProductResponseDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductRepositoryImplTest {

    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private val remoteDataSource: RemoteDataSource = mockk()
    private val repository = ProductRepositoryImpl(localDataSource, remoteDataSource)

    @Test
    fun `observeProducts maps local entities to domain products`() = runTest {
        every {
            localDataSource.observeProducts()
        } returns flowOf(
            listOf(
                productEntity(
                    id = 1L,
                    title = "Product 1",
                    price = 100,
                ),
                productEntity(
                    id = 2L,
                    title = "Product 2",
                    price = 200,
                )
            )
        )
        val products = repository.observeProducts().first()
        assertEquals(2, products.size)
        assertEquals("Product 1", products[0].title)
        assertEquals("Product 2", products[1].title)

        assertEquals(100, products[0].priceInPence)
        assertEquals(200, products[1].priceInPence)

    }

    @Test
    fun `observeProduct maps local entity to domain product`() = runTest {
        every {
            localDataSource.observeProduct(1L)
        } returns flowOf(
            productEntity(
                id = 1L,
                title = "Product 1",
                price = 100,
            )
        )
        val product = repository.observeProduct(1L).first()
        assertEquals("Product 1", product?.title)
        assertEquals(100, product?.priceInPence)
    }

    @Test
    fun `refreshProducts saves remote products to local and returns success`() = runTest {
        coEvery {
            remoteDataSource.getProducts()
        } returns DataResult.Success(
            ProductResponseDto(
                hits = listOf(
                    productDto(
                        id = 1L,
                        title = "Product 1",
                        price = 100,
                    ),
                    productDto(
                        id = 2L,
                        title = "Product 2",
                        price = 200,
                    )
                )
            )
        )
        coEvery {
            localDataSource.replaceProducts(any())
        } returns Unit
        val result = repository.refreshProducts()
        assertTrue(result is DataResult.Success)
        coVerify(exactly = 1) {
            remoteDataSource.getProducts()
        }
        coVerify(exactly = 1) {
            localDataSource.replaceProducts(
                match { entities ->
                    entities.size == 2 &&
                            entities[0].title == "Product 1" &&
                            entities[1].title == "Product 2"
                }
            )
        }
    }

    @Test
    fun `refreshProducts returns error when remote data source fails`() = runTest {
        coEvery {
            remoteDataSource.getProducts()
        } returns DataResult.Error(AppError.Network.NoInternet)
        val result = repository.refreshProducts()
        assertTrue(result is DataResult.Error)
        assertEquals(
            AppError.Network.NoInternet, (result as DataResult.Error).error
        )
        coVerify(exactly = 0) {
            localDataSource.replaceProducts(any())
        }
    }

    @Test
    fun `refreshProducts returns error when saving product fails`() = runTest {
        coEvery {
            remoteDataSource.getProducts()
        } returns DataResult.Success(
            ProductResponseDto(
                hits = listOf(
                    productDto(
                        id = 1L,
                        title = "Product 1",
                        price = 100,
                    )
                )
            )
        )
        coEvery {
            localDataSource.replaceProducts(any())
        } throws RuntimeException("Database error")
        val result = repository.refreshProducts()
        assertTrue(result is DataResult.Error)
        assertEquals(
            AppError.Local.Unknown, (result as DataResult.Error).error
        )
    }

}

private fun productEntity(
    id: Long = 1L,
    price: Int = 0,
    title: String = "Speed Leggings",
): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        inStock = true,
        imageUrl = "",
        heroImageUrls = emptyList(),
        type = "",
        labels = emptyList(),
        gender = emptyList(),
        sizeInStock = emptyList(),
        availableSizes = emptyList(),
        description = "",
        position = 0,
        sku = "",
        handle = "",
        colour = "",
    )
}

fun productDto(
    id: Long = 1L,
    price: Int = 0,
    title: String = "Speed Leggings",
): ProductDto {
    return ProductDto(
        id = id,
        title = title,
        price = price,
        inStock = true,
        media = emptyList(),
        featuredMedia = null,
        sizeInStock = emptyList(),
        availableSizes = emptyList(),
        description = "",
        type = "",
        labels = emptyList(),
        gender = emptyList(),
        sku = "",
        handle = "",
        colour = "",
        objectID = "",
        fit = null,
        compareAtPrice = null,
        discountPercentage = null,
    )

}