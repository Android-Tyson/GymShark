package com.historymakers.gymshark.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.historymakers.gymshark.data.local.ProductDatabase
import com.historymakers.gymshark.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDatabase(
        @ApplicationContext context: Context
    ): ProductDatabase {
        return Room.databaseBuilder(
                context,
                ProductDatabase::class.java,
                "product_database"
            ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(
        productDatabase: ProductDatabase
    ): ProductDao {
        return productDatabase.productDao
    }
}