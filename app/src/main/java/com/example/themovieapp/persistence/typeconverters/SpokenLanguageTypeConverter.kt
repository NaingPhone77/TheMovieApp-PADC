package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.SpokenLanguagesVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpokenLanguageTypeConverter {

    @TypeConverter
    fun toString(spokenLanguage : List<SpokenLanguagesVO>?) : String {
        return Gson().toJson(spokenLanguage)
    }

    @TypeConverter
    fun toSpokenLanguage(spokenLanguageJsonString : String) : List<SpokenLanguagesVO>? {
        val spokenLanguageListVOType = object : TypeToken<List<SpokenLanguagesVO>?>(){}.type
        return Gson().fromJson(spokenLanguageJsonString, spokenLanguageListVOType)
    }
}