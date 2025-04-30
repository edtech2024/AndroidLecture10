package com.example.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DateConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<Long>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String?): List<Long> {
        return gson.fromJson(json, object : TypeToken<List<Long>>() {}.type)
    }

}