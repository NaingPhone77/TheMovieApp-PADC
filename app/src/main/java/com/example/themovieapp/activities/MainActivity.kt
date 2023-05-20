package com.example.themovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapter.BannerAdapter
import com.example.themovieapp.adapter.ShowcaseAdapter
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.databinding.ActivityMainBinding
import com.example.themovieapp.delegates.BannerViewHolderDelegate
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.delegates.ShowcaseViewHolderDelegate
import com.example.themovieapp.dummy.dummyGenreList
import com.example.themovieapp.network.dataagents.MovieDataAgent
import com.example.themovieapp.network.dataagents.MovieDataAgentImpl
//import com.example.themovieapp.network.dataagents.OkHttpDataAgentImpl
import com.example.themovieapp.network.dataagents.RetrofitDataAgentImpl
import com.example.themovieapp.viewpods.ActorListsViewPod
import com.example.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, ShowcaseViewHolderDelegate, MovieViewHolderDelegate {

    private lateinit var binding : ActivityMainBinding

    //Global Variables
    private lateinit var mBannerAdapter : BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter

    private lateinit var mBestPopularMovieListViewPod : MovieListViewPod
    lateinit var mMoviesByGenreViewPod : MovieListViewPod
    lateinit var mActorListViewPod : ActorListsViewPod

    //model
    private val mMovieModel : MovieModel = MovieModelImpl

    //data    (to save Genres)
    private var mGenres : List<GenreVO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolBar()
        setUpBannerViewPaper()
//        setUpGenreTabLayout()
        setUpShowcaseRecyclerView()
        setUpViewPods()
        setUpListeners()
        requestData()

//        MovieDataAgentImpl.getNowPlayingMovies()
//        OkHttpDataAgentImpl.getNowPlayingMovies()
//        RetrofitDataAgentImpl.getNowPlayingMovies()
    }

    private fun requestData(){

        //Now Playing Movie
        mMovieModel.getNowPlayingMovies(
            onSuccess = {
                mBannerAdapter.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "Now Playing Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )

        //Popular Movie
        mMovieModel.getPopularMovies(
            onSuccess = {
                mBestPopularMovieListViewPod.setData(it)
            },
            onFailure = {
                Toast.makeText(this, "Popular Section Failed", Toast.LENGTH_SHORT).show()
            }
        )

        //Top Rated Movie
        mMovieModel.getTopRatedMovies(
            onSuccess = {
                mShowcaseAdapter.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "Top rated Failed", Toast.LENGTH_SHORT).show()
            }
        )

        // Get Genres
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                setUpGenreTabLayout(it)

                // Get Movies By Genre For First Genre
                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenre(genreId)
                }
            },
            onFailure = {
                Toast.makeText(this, "Genre Section Failed", Toast.LENGTH_SHORT).show()
            }
        )

        // Actors
        mMovieModel.getActors(
            onSuccess = {
                mActorListViewPod.setData(it)
            },
            onFailure = {
                Toast.makeText(this, "Actors Section Failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // to get Genre Movie ID
    private fun getMoviesByGenre(genreId : Int){
        mMovieModel.getMoviesByGenre(
            genreId = genreId.toString(),
            onSuccess = {
                mMoviesByGenreViewPod.setData(it)
            },
            onFailure = {
                Toast.makeText(this, "Movie By Genre Failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    //setup instance of composite custom view(View Pod) to use in activity
    private fun setUpViewPods(){
        mBestPopularMovieListViewPod = binding.vpBestPopularMovieList.root
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = binding.vpMoviesByGenre.root
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mActorListViewPod = binding.vpBestActors.root
        mActorListViewPod.setUpActorListViewPod()
    }

    private fun setUpListeners() {

        //listen to Genre TabLayout
        //TabLayout.OnTabSelectedListener is listener. So we should use "object"
        binding.tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mGenres?.get(tab?.position ?: 0)?.id?.let {
                    getMoviesByGenre(it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setUpShowcaseRecyclerView(){
        mShowcaseAdapter = ShowcaseAdapter(this)
        binding.rvShowcases.adapter = mShowcaseAdapter
        binding.rvShowcases.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpBannerViewPaper() {
        mBannerAdapter = BannerAdapter(this)
        binding.viewPagerBanner.adapter = mBannerAdapter

        //attach dots indicator with view pager
        binding.dotsIndicatorBanner.attachTo(binding.viewPagerBanner)
    }

    // AppBar leading Icon
    private fun setUpToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setUpGenreTabLayout(genreList : List<GenreVO>){
        genreList.forEach {
            binding.tabLayoutGenre.newTab().apply {
                text = it.name
                binding.tabLayoutGenre.addTab(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover,menu)
        return true
    }

    override fun onTapMovieFromBanner(movieId : Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId))
        Snackbar.make(window.decorView, "Tapped Movie From Banner", Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
        Snackbar.make(window.decorView, "Tapped Movie From Showcase", Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
        Snackbar.make(window.decorView, "Tapped Movie From Movie", Snackbar.LENGTH_LONG).show()
    }


    private fun setUpTextListener(){
        binding.tvCheckMovieShowTimeLabel.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })
    }

//        private fun showErrorMessage() {
//            Toast.makeText(this,"Network Failed",Toast.LENGTH_LONG).show()
//    }
}