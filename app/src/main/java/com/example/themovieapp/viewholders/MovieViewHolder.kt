package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ViewHolderMovieBinding
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.utils.IMAGE_BASE_URL

// Movie VH is for Genre and popular movie in screen

class MovieViewHolder(itemView: View,private val mDelegate: MovieViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var binding : ViewHolderMovieBinding

    private var mMovieVO : MovieVO? = null     // to bind Global var and movie to get movie ID

    init {
            binding = ViewHolderMovieBinding.bind(itemView)
            itemView.setOnClickListener {
                mMovieVO?.let {  movie ->
                    mDelegate.onTapMovie(movie.id)
                }
            }
        }

    fun bindData(movie: MovieVO){
        mMovieVO = movie
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMovieImage)
        binding.tvMovieName.text = movie.title
        binding.tvMovieRating.text = movie.voteAverage?.toString()
        binding.rbMovieRating.rating = movie.getRatingBarBaseOnFiveStars()
    }
}