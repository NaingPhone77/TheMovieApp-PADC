package com.example.themovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.R
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.viewholders.MovieViewHolder

class MovieAdapter(private val mDelegate : MovieViewHolderDelegate) : RecyclerView.Adapter<MovieViewHolder>() {

    private var mMovieList : List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        return MovieViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (mMovieList.isNotEmpty()){
            holder.bindData(mMovieList[position])
        }
    }

    override fun getItemCount(): Int {
        return if (mMovieList.count() > 5){
            5
        }else{
            mMovieList.count()
        }
    }

    fun setNewData(movieList : List<MovieVO>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }
}