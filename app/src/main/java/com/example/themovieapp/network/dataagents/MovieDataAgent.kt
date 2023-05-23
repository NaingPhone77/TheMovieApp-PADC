package com.example.themovieapp.network.dataagents

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.GetActorsResponse

interface MovieDataAgent {
    fun getNowPlayingMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getPopularMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getTopRatedMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMoviesByGenre(
        genreId : String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetails(
        movieId : String,                        // movieId comes from View Layer
        onSuccess : (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCreditsByMovie(
        movieId : String,
        onSuccess : (Pair<List<ActorVO>, List<ActorVO>>)    -> Unit,         // want to send (2)lists to Data layer
        onFailure: (String) -> Unit
    )
}