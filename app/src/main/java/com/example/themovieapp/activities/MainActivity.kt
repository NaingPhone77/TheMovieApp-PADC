package com.example.themovieapp.activities

//import com.example.themovieapp.network.dataagents.MovieDataAgent
//import com.example.themovieapp.network.dataagents.MovieDataAgentImpl
//import com.example.themovieapp.network.dataagents.OkHttpDataAgentImpl
//import com.example.themovieapp.network.dataagents.RetrofitDataAgentImpl
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
import com.example.themovieapp.mvvm.MainViewModel
import com.example.themovieapp.viewpods.ActorListsViewPod
import com.example.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(),BannerViewHolderDelegate,ShowcaseViewHolderDelegate,MovieViewHolderDelegate{

    private lateinit var binding : ActivityMainBinding

    //Global Variables (VIEW PODS)
    lateinit var mBannerAdapter : BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter

    lateinit var mBestPopularMovieListViewPod : MovieListViewPod
    lateinit var mMoviesByGenreViewPod : MovieListViewPod
    lateinit var mActorListViewPod : ActorListsViewPod

    //model
    private val mMovieModel : MovieModel = MovieModelImpl

    //View Model
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()

        setUpToolBar()
        setUpBannerViewPaper()
//        setUpGenreTabLayout()
        setUpListeners()
        setUpShowcaseRecyclerView()
        setUpViewPods()

        // Observe Live Data
        observeLiveData()
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getInitialData()
    }

    private fun observeLiveData(){

        // other way
//        mViewModel.nowPlayingMovieLiveData?.observe(this) {
//            mBannerAdapter.setNewData(it)
//        }

        mViewModel.nowPlayingMovieLiveData?.observe(this,mBannerAdapter::setNewData)
        mViewModel.popularMovieLiveData?.observe(this,mBestPopularMovieListViewPod::setData)
        mViewModel.topRatedMovieLiveData?.observe(this,mShowcaseAdapter::setNewData)
        mViewModel.genresLiveData?.observe(this,this::setUpGenreTabLayout)
        mViewModel.moviesByGenreLiveData?.observe(this,mMoviesByGenreViewPod::setData)
        mViewModel.actorsLiveData?.observe(this,mActorListViewPod::setData)
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
                mViewModel.getMovieByGenre(tab?.position ?: 0)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.btnSearch) {
            startActivity(MovieSearchActivity.newIntent(this))
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


    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
    }
}
