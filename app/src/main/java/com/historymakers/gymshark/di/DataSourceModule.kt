package com.historymakers.gymshark.di

import com.historymakers.gymshark.data.datasource.LocalDataSource
import com.historymakers.gymshark.data.datasource.LocalDataSourceImpl
import com.historymakers.gymshark.data.datasource.RemoteDataSource
import com.historymakers.gymshark.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

}