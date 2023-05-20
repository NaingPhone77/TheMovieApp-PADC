package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ViewHolderShowcaseBinding
import com.example.themovieapp.delegates.ShowcaseViewHolderDelegate
import com.example.themovieapp.utils.IMAGE_BASE_URL

class ShowcaseViewHolder(itemView: View, private val mDelegate: ShowcaseViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ViewHolderShowcaseBinding

    private var mMovieVO : MovieVO? = null     // to bind Global var and movie to get movie ID

        init {
            binding = ViewHolderShowcaseBinding.bind(itemView)
            itemView.setOnClickListener {
                mMovieVO?.let {  movie ->
                    mDelegate.onTapMovieFromShowcase(movie.id)
                }
            }
        }

    fun bindData(movie: MovieVO){
        mMovieVO = movie
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivShowcase)
        binding.ivShowCaseMovieName.text = movie.title
        binding.ivShowCaseMovieDate.text = movie.releaseDate
    }
}