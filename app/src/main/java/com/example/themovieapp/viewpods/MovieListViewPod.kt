package com.example.themovieapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.adapter.MovieAdapter
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ViewPodMovieListBinding
import com.example.themovieapp.delegates.MovieViewHolderDelegate

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private lateinit var binding : ViewPodMovieListBinding

    private lateinit var mMovieAdapter : MovieAdapter
    private lateinit var mDelegate : MovieViewHolderDelegate

    override fun onFinishInflate() {
       // setUpMovieRecyclerView()
        super.onFinishInflate()
        binding = ViewPodMovieListBinding.bind(this)
    }

    fun setUpMovieListViewPod(delegate: MovieViewHolderDelegate){
        setDelegate(delegate)
        setUpMovieRecyclerView()
    }

    fun setData(movieList : List<MovieVO>){
        mMovieAdapter.setNewData(movieList)
    }

    private fun setDelegate(delegate: MovieViewHolderDelegate){
        this.mDelegate = delegate
    }

    private fun setUpMovieRecyclerView(){
        mMovieAdapter = MovieAdapter(mDelegate)
        binding.rvMovieList.adapter = mMovieAdapter
        binding.rvMovieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
    }
}