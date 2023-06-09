package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {

    //View
    var mView : MainView ? = null

    //Model
    private var mMovieModel : MovieModel  = MovieModelImpl

    //States
    private var mGenres : List<GenreVO> ? = listOf()

    override fun initView(view: MainView) {
        mView = view
    }


    override fun onUiReady(owner: LifecycleOwner) {
        // Now Playing Movies
        mMovieModel.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        // Popular Movies
        mMovieModel.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        // Top Rated Movies
        mMovieModel.getTopRatedMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        // Genre and Get Movie for First Genre
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let {  firstGenreId ->
                    onTapGenre(firstGenreId)
                }
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        // Actors
        mMovieModel.getActors(
            onSuccess = {
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres?.getOrNull(genrePosition)?.id?.let {  genreId ->
            mMovieModel.getMoviesByGenre(
                genreId = genreId.toString(),
                onSuccess = {
                    mView?.showMoviesByGenre(it)
                },
                onFailure = {
                    mView?.showError(it)
                }
            )
        }
    }
}