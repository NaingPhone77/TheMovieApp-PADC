package com.example.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class MovieVO(
    @SerializedName("adult")
    val adult : Boolean?,

    @SerializedName("backdrop_path")
    val backDropPath : String?,

    @SerializedName("genre_ids")
    val genres : List<GenreVO>?,

    @SerializedName("id")
    val id : Int,

    @SerializedName("original_language")
    val originalLanguage : String?,

    @SerializedName("original_title")
    val originalTitle : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("popularity")
    val popularity : Double?,

    @SerializedName("poster_path")
    val posterPath : String?,

    @SerializedName("release_date")
    val releaseDate : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("video")
    val video : Boolean?,

    @SerializedName("vote_average")
    val voteAverage : Double?,

    @SerializedName("vote_count")
    val voteCount : Int?,

    @SerializedName("production_companies")
    val productionCompanies : List<ProductionCompanyVO>?,

    @SerializedName("production_countries")
    val productionCountries : List<ProductionCountryVO>?,

    @SerializedName("revenue")
    val revenue : Int?,

    @SerializedName("runtime")
    val runtime : Int?,

    @SerializedName("spoken_languages")
    val spokenLanguages : List<SpokenLanguagesVO>?,

    @SerializedName("status")
    val status : String?,

    @SerializedName("tagline")
    val tagline : String?

) {
    fun getRatingBarBaseOnFiveStars() : Float{
        return voteAverage?.div(2)?.toFloat() ?: 0.0F
    }

    fun getGenresAsCommaSeparatedString(): String{
        return genres?.map { it.name }?.joinToString(",") ?: ""
    }

    fun getCountriesAsCommaSeparatedString() : String {
        return productionCountries?.map { it.name }?.joinToString(",") ?: ""
    }
}
