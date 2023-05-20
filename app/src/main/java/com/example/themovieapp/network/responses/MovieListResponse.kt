package com.example.themovieapp.network.responses

import com.example.themovieapp.data.vos.DateVO
import com.example.themovieapp.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

// start with { curly bracket. So this is obj
data class MovieListResponse(
    @SerializedName("dates")
    val dates : DateVO?,

    @SerializedName("page")
    val page : Int?,

    @SerializedName("results")
    val results : List<MovieVO>?
)
