package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.example.themovieapp.mvp.views.MovieDetailView

interface MovieDetailsPresenter : IBasePresenter {
    fun initView(view : MovieDetailView)
    fun onUiReadyInMovieDetails(owner : LifecycleOwner, movieId : Int)
    fun onTapBack()
}