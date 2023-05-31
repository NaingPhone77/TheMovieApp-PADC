package com.example.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovieapp.adapter.MovieAdapter
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.databinding.ActivityMovieSearchBinding
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity() , MovieViewHolderDelegate  {

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context,MovieSearchActivity::class.java)
        }
    }

    private lateinit var binding : ActivityMovieSearchBinding

    private lateinit var mMovieAdapter: MovieAdapter
    private val mMovieModel : MovieModel = MovieModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
        setUpRecyclerView()

    }

    private fun setUpListeners() {
        binding.etSearch.textChanges()
            .debounce(500L,TimeUnit.MILLISECONDS)
            .flatMap {
                mMovieModel.searchMovie(it.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                mMovieAdapter.setNewData(it)
            },
                {
                    showError(it.localizedMessage?: "")
                })
    }


    private fun setUpRecyclerView(){
        mMovieAdapter = MovieAdapter(this)
        binding.rvSearchMovies.adapter = mMovieAdapter
        binding.rvSearchMovies.layoutManager = GridLayoutManager(this,2)
    }


    override fun onTapMovie(movieId: Int) {
    }

    private fun showError(error: String) {
        Toast.makeText(this,error, Toast.LENGTH_LONG).show()
    }
}