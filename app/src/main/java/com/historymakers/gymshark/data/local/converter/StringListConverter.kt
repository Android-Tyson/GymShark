package com.historymakers.gymshark.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return gson.toJson(list.orEmpty())
    }
}