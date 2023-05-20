package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ViewItemBannerBinding
import com.example.themovieapp.delegates.BannerViewHolderDelegate
import com.example.themovieapp.utils.IMAGE_BASE_URL

class BannerViewHolder(itemView: View, private val mDelegate: BannerViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var binding : ViewItemBannerBinding

    private var mMovieVO : MovieVO? = null     // to bind Global var and movie to get movie ID

        init {
            binding = ViewItemBannerBinding.bind(itemView)
            itemView.setOnClickListener {
                mMovieVO?.let {  movie ->
                    mDelegate.onTapMovieFromBanner(movie.id)
                }
            }
        }

    fun bindData(movie: MovieVO){
        mMovieVO = movie
        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${movie.posterPath}")
            .into(binding.ivBannerImage)

        binding.tvBannerMovieName.text = movie.title
    }


}