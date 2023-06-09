package com.example.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.MovieVO

class MovieDetailsViewModel : ViewModel() {
    // Model
    private val mMovieModel : MovieModel = MovieModelImpl

    // Live Data
    var movieDetailLiveData : LiveData<MovieVO?>? = null
    val castLiveData = MutableLiveData<List<ActorVO>>()
    val crewLiveData = MutableLiveData<List<ActorVO>>()
    val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId : Int) {
        movieDetailLiveData =
            mMovieModel.getMovieDetails(movieId = movieId.toString()) { mErrorLiveData.postValue(it) }

        mMovieModel.getCreditsByMovie(movieId = movieId.toString(), onSuccess = {
            castLiveData.postValue(it.first ?: listOf())
            crewLiveData.postValue(it.second ?: listOf())
        }, onFailure = {
            mErrorLiveData.postValue(it)
        })
    }
}