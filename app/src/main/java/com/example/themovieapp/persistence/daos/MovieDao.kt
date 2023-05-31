package com.example.themovieapp.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovieapp.data.vos.MovieVO

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies : List<MovieVO>)                    // For Main Screen

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie : MovieVO?)                     // For Detail Screen

    @Query("SELECT * FROM movies")
    fun getAllMovies() : LiveData<List<MovieVO>>              // Movies List

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId : Int) : LiveData<MovieVO?>                // movie ID

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieByIdOneTime(movieId : Int) : MovieVO?

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMoviesByType(type : String) : LiveData<List<MovieVO>>          // type

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}