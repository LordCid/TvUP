package com.example.omapp.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromListStringToString(value: List<String>): String {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun stringToListString(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}