package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.interactors.MovieInteractor
import com.example.themovieapp.interactors.MovieInteractorImpl
import com.example.themovieapp.mvp.views.MovieDetailView

class MovieDetailsPresenterImpl : ViewModel(), MovieDetailsPresenter {

    // Interactor
    private val mMovieInteractor : MovieInteractor = MovieInteractorImpl

    // View
    private var mView: MovieDetailView? = null


    override fun initView(view: MovieDetailView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {

        // Movie Details
        mMovieInteractor.getMovieDetails(movieId.toString()) {
            mView?.showError(it)
        }?.observe(owner) {
            it?.let {
                mView?.showMovieDetails(it)
            }
        }

        // Get Credit Movies
        mMovieInteractor.getCreditsByMovie(
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