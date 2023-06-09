package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.mvp.views.MovieDetailView

class MovieDetailsPresenterImpl : ViewModel(), MovieDetailsPresenter {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // View
    private var mView: MovieDetailView? = null


    override fun initView(view: MovieDetailView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {

        // Movie Details
        mMovieModel.getMovieDetails(movieId.toString()) {
            mView?.showError(it)
        }?.observe(owner) {
            it?.let {
                mView?.showMovieDetails(it)
            }
        }

        // Get Credit Movies
        mMovieModel.getCreditsByMovie(
            movieId.toString(),
            onSuccess = {
                mView?.showCreditsByMovie(cast = it.first, crew = it.second)
            },
            onFailure = {
                mView?.showError(it)
            })
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onUiReady(owner: LifecycleOwner) {}
}