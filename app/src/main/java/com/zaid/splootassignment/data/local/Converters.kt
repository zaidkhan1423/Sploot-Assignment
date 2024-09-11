package com.zaid.splootassignment.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zaid.splootassignment.data.model.response.Source

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String?): Source? {
        return gson.fromJson(sourceString, Source::class.java)
    }
}