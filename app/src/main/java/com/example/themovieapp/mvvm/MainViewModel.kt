package com.example.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

class MainViewModel : ViewModel() {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    //Live Data
    var nowPlayingMovieLiveData: LiveData<List<MovieVO>>? = null
    var popularMovieLiveData: LiveData<List<MovieVO>>? = null
    var topRatedMovieLiveData: LiveData<List<MovieVO>>? = null

    val genresLiveData = MutableLiveData<List<GenreVO>>()
    val moviesByGenreLiveData = MutableLiveData<List<MovieVO>>()
    val actorsLiveData = MutableLiveData<List<ActorVO>>()
    val mErrorLiveData = MutableLiveData<String>()


    fun getInitialData() {
        nowPlayingMovieLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        popularMovieLiveData = mMovieModel.getPopularMovies { mErrorLiveData.postValue(it) }
        topRatedMovieLiveData = mMovieModel.getTopRatedMovies { mErrorLiveData.postValue(it) }

        mMovieModel.getGenres(
            onSuccess = {
                genresLiveData.postValue(it)
                getMovieByGenre(0)
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )

        mMovieModel.getActors(
            onSuccess = {
                actorsLiveData.postValue(it)
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )
    }

     fun getMovieByGenre(genrePosition: Int) {
        genresLiveData.value?.getOrNull(genrePosition)?.id?.let {
            mMovieModel.getMoviesByGenre(it.toString(), onSuccess = { movieByGenre ->
                moviesByGenreLiveData.postValue(movieByGenre)
            },
                onFailure = { errorMessage ->
                    mErrorLiveData.postValue(errorMessage)
                })
        }
    }
}
