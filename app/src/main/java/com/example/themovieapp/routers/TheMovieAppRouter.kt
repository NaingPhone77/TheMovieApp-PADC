package com.example.themovieapp.routers

import android.app.Activity
import com.example.themovieapp.activities.MovieDetailsActivity
import com.example.themovieapp.activities.MovieSearchActivity

fun Activity.navigateToMovieDetailActivity(movieId : Int) {
    startActivity(MovieDetailsActivity.newIntent(this,movieId))
}


fun Activity.navigateToSearchActivity(){
    startActivity(MovieSearchActivity.newIntent(this))
}