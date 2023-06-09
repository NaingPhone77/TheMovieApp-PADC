package com.example.themovieapp.mvp.views

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.MovieVO

interface MovieDetailView : BaseView{

    fun showMovieDetails(movie : MovieVO)
    fun showCreditsByMovie(cast : List<ActorVO>, crew : List<ActorVO>)
    fun navigateBack()

}