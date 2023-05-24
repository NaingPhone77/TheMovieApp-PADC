package com.example.themovieapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.themovieapp.persistence.*
import com.example.themovieapp.persistence.typeconverters.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
@TypeConverters(
    CollectionTypeConverter::class,
    GenreIdsTypeConverter::class,
    GenreListTypeConverter::class,
    ProductionCompanyTypeConverter::class,
    ProductionCountryTypeConverter::class,
    SpokenLanguageTypeConverter::class
)

data class MovieVO(

    @SerializedName("adult")
    @ColumnInfo("adult")
    val adult : Boolean?,

    @SerializedName("backdrop_path")
    @ColumnInfo("backdrop_path")
    val backDropPath : String?,

    @SerializedName("belongs_to_collection")
    @ColumnInfo(name = "belongs_to_collection")
    val belongsToCollection: CollectionVO?,

    @SerializedName("budget")
    @ColumnInfo(name = "budget")
    val budget: Double?,

    @SerializedName("homepage")
    @ColumnInfo(name = "homepage")
    val homepage: String?,

    @SerializedName("imdb_id")
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,

    @SerializedName("genre_ids")
    @ColumnInfo("genre_ids")
    val genreId : List<Int>?,

    @SerializedName("genres")
    @ColumnInfo("genres")
    val genres : List<GenreVO>?,

    @SerializedName("id")
    @PrimaryKey
    val id : Int = 0,

    @SerializedName("original_language")
    @ColumnInfo("original_language")
    val originalLanguage : String?,

    @SerializedName("original_title")
    @ColumnInfo("original_title")
    val originalTitle : String?,

    @SerializedName("overview")
    @ColumnInfo("overview")
    val overview : String?,

    @SerializedName("popularity")
    @ColumnInfo("popularity")
    val popularity : Double?,

    @SerializedName("poster_path")
    @ColumnInfo("poster_path")
    val posterPath : String?,

    @SerializedName("release_date")
    @ColumnInfo("release_date")
    val releaseDate : String?,

    @SerializedName("title")
    @ColumnInfo("title")
    val title : String?,

    @SerializedName("video")
    @ColumnInfo("video")
    val video : Boolean?,

    @SerializedName("vote_average")
    @ColumnInfo("vote_average")
    val voteAverage : Double?,

    @SerializedName("vote_count")
    @ColumnInfo("vote_count")
    val voteCount : Int?,

    @SerializedName("production_companies")
    @ColumnInfo("production_companies")
    val productionCompanies : List<ProductionCompanyVO>?,

    @SerializedName("production_countries")
    @ColumnInfo("production_countries")
    val productionCountries : List<ProductionCountryVO>?,

    @SerializedName("revenue")
    @ColumnInfo("revenue")
    val revenue : Int?,

    @SerializedName("runtime")
    @ColumnInfo("runtime")
    val runtime : Int?,

    @SerializedName("spoken_languages")
    @ColumnInfo("spoken_languages")
    val spokenLanguages : List<SpokenLanguagesVO>?,

    @SerializedName("status")
    @ColumnInfo("status")
    val status : String?,

    @SerializedName("tagline")
    @ColumnInfo("tagline")
    val tagline : String?,

    @ColumnInfo("type")
    var type : String?

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

const val NOW_PLAYING = "NOW_PLAYING"
const val POPULAR = "POPULAR"
const val TOP_RATED = "TOP_RATED"
