package com.example.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapter.BannerAdapter
import com.example.themovieapp.adapter.ShowcaseAdapter
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ActivityMainBinding
import com.example.themovieapp.mvp.presenters.MainPresenter
import com.example.themovieapp.mvp.presenters.MainPresenterImpl
import com.example.themovieapp.mvp.views.MainView
import com.example.themovieapp.routers.navigateToMovieDetailActivity
import com.example.themovieapp.routers.navigateToSearchActivity
import com.example.themovieapp.viewpods.ActorListsViewPod
import com.example.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity(),MainView{

    private lateinit var binding : ActivityMainBinding

    //Global Variables (VIEW PODS)
    lateinit var mBannerAdapter : BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter

    lateinit var mBestPopularMovieListViewPod : MovieListViewPod
    lateinit var mMoviesByGenreViewPod : MovieListViewPod
    lateinit var mActorListViewPod : ActorListsViewPod

    // Presenter
    private lateinit var mPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPresenter()

        setUpToolBar()
        setUpViewPods()
        setUpBannerViewPaper()
//        setUpGenreTabLayout()
        setUpListeners()
        setUpShowcaseRecyclerView()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }


    //setup instance of composite custom view(View Pod) to use in activity
    private fun setUpViewPods(){
        mBestPopularMovieListViewPod = binding.vpBestPopularMovieList.root
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = binding.vpMoviesByGenre.root
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mActorListViewPod = binding.vpBestActors.root
        mActorListViewPod.setUpActorListViewPod()
    }


    private fun setUpListeners() {

        //listen to Genre TabLayout
        //TabLayout.OnTabSelectedListener is listener. So we should use "object"
        binding.tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setUpShowcaseRecyclerView(){
        mShowcaseAdapter = ShowcaseAdapter(mPresenter)
        binding.rvShowcases.adapter = mShowcaseAdapter
        binding.rvShowcases.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpBannerViewPaper() {
        mBannerAdapter = BannerAdapter(mPresenter)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.btnSearch) {
            navigateToSearchActivity()
            return true
        }
        return super.onOptionsItemSelected(item)

        //        val id = item.itemId
//        if (id == R.id.menuSearch) {
//            Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show()
//            startActivity( MovieSearchActivity.newIntent(this))
//
//            return true
//        }
//        return super.onOptionsItemSelected(item)
    }




//
//    private fun setUpTextListener(){
//        binding.tvCheckMovieShowTimeLabel.addTextChangedListener( object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
//
//            override fun afterTextChanged(s: Editable?) { }
//
//        })
//    }

    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showPopularMovies(popularMovies: List<MovieVO>) {
        mBestPopularMovieListViewPod.setData(popularMovies)
    }

    override fun showTopRatedMovies(topRatedMovies: List<MovieVO>) {
        mShowcaseAdapter.setNewData(topRatedMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showMoviesByGenre(moviesByGenre: List<MovieVO>) {
        mMoviesByGenreViewPod.setData(moviesByGenre)
    }

    override fun showActors(actors: List<ActorVO>) {
        mActorListViewPod.setData(actors)
    }

    override fun navigateToMovieDetailScreen(movieId: Int) {
        navigateToMovieDetailActivity(movieId)
    }

    override fun showError(errorString: String) {
        Toast.makeText(this, "VIPER FAILED", Toast.LENGTH_SHORT).show()
    }
}
