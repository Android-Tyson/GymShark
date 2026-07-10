package com.historymakers.gymshark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.historymakers.gymshark.data.local.converter.StringListConverter
import com.historymakers.gymshark.data.local.dao.ProductDao
import com.historymakers.gymshark.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 2, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}