package com.example.themovieapp.data.models

import android.content.Context
import com.example.themovieapp.data.vos.*
import com.example.themovieapp.network.dataagents.MovieDataAgent
import com.example.themovieapp.network.dataagents.RetrofitDataAgentImpl
import com.example.themovieapp.persistence.MovieDatabase

object MovieModelImpl : MovieModel {

    //Data Pass from Network layer to Data Layer
    private val mMovieDataAgent : MovieDataAgent = RetrofitDataAgentImpl   // Data Agent is Interface type. So call MovieDataAgent

    //Room Database
    private var mMovieDatabase : MovieDatabase ?= null

    fun initDatabase(context: Context){
        mMovieDatabase = MovieDatabase.getDBInstance(context)
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Database (Show Persistence layer's Data in ViewLayer)
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = NOW_PLAYING) ?: listOf())

        //Network
        mMovieDataAgent.getNowPlayingMovies(onSuccess = {

            it.forEach { movie -> movie.type = NOW_PLAYING }     // put data to persistence layer
            mMovieDatabase?.movieDao()?.insertMovies(it)         // put data to persistence layer

            onSuccess(it)                                      // send data to View Layer from Network layer
        }, onFailure = onFailure)


        //Data Layer and Network layer code
//        mMovieDataAgent.getNowPlayingMovies(onSuccess = onSuccess, onFailure = onFailure)     // req Data from Network Layer
    }

    override fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = POPULAR) ?: listOf())

        mMovieDataAgent.getPopularMovies(onSuccess ={

            it.forEach { movie -> movie.type = POPULAR }
            mMovieDatabase?.movieDao()?.insertMovies(it)

            onSuccess(it)
        }, onFailure = onFailure)

//        mMovieDataAgent.getPopularMovies(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = TOP_RATED) ?: listOf())

        mMovieDataAgent.getTopRatedMovies(onSuccess = {

            it.forEach { movie -> movie.type = TOP_RATED }
            mMovieDatabase?.movieDao()?.insertMovies(it)

            onSuccess(it)
        }, onFailure= onFailure)
//        mMovieDataAgent.getTopRatedMovies(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getGenres(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getMoviesByGenre(genreId = genreId, onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getActors(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Database   (Show Persistence layer's Data in ViewLayer)
        val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        movieFromDatabase?.let {
            onSuccess(it)
        }

        // Network
        mMovieDataAgent.getMovieDetails(
            movieId = movieId,
            onSuccess = {

                val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())    // At first, take a movie from Persistence layer
                it.type = movieFromDatabase?.type             //  put a movie's type that from the persistence layer into the type of Network call  <- ( type that get from network call)
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)      // put data to persistence layer

                onSuccess(it)       //show in view layer
            },
            onFailure = onFailure
        )

//        mMovieDataAgent.getMovieDetails(movieId = movieId , onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getCreditsByMovie(movieId = movieId, onSuccess = onSuccess, onFailure = onFailure)
    }
}