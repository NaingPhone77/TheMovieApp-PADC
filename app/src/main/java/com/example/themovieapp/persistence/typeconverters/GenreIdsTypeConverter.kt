package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsTypeConverter {
    @TypeConverter
    fun toString(genreIds : List<Int>?) : String {
        return Gson().toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIds(genreIdsJsonString : String) : List<Int>? {
        val genreIdsVOType = object : TypeToken<List<Int>?>() {}.type
        return Gson().fromJson(genreIdsJsonString, genreIdsVOType)
    }
}