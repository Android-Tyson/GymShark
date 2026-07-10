package com.historymakers.gymshark.di

import com.historymakers.gymshark.data.repo.ProductRepositoryImpl
import com.historymakers.gymshark.domain.repo.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

}