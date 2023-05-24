package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.ProductionCountryVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCountryTypeConverter {

    @TypeConverter
    fun toString( productionCountry : List<ProductionCountryVO>?) : String {
        return Gson().toJson(productionCountry)
    }

    @TypeConverter
    fun toProductionCountry(productionCountryJsonString : String) : List<ProductionCountryVO>? {
        val productionCountryVOType = object : TypeToken<List<ProductionCountryVO>?>() {}.type
        return Gson().fromJson(productionCountryJsonString, productionCountryVOType)
    }
}