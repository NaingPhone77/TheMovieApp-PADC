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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapter.BannerAdapter
import com.example.themovieapp.adapter.ShowcaseAdapter
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ActivityMainBinding
import com.example.themovieapp.mvp.presenters.MainPresenter
import com.example.themovieapp.mvp.presenters.MainPresenterImpl
import com.example.themovieapp.mvp.views.MainView
import com.example.themovieapp.viewpods.ActorListsViewPod
import com.example.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), MainView{

    private lateinit var binding : ActivityMainBinding

    //Global Variables (VIEW PODS)
    lateinit var mBannerAdapter : BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter

    lateinit var mBestPopularMovieListViewPod : MovieListViewPod
    lateinit var mMoviesByGenreViewPod : MovieListViewPod
    lateinit var mActorListViewPod : ActorListsViewPod

    //data    (to save Genres)
    private var mGenres : List<GenreVO>? = null

    // Presenter
    private lateinit var mPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPresenter()

        setUpToolBar()
        setUpBannerViewPaper()
//        setUpGenreTabLayout()
        setUpListeners()
        setUpShowcaseRecyclerView()
        setUpViewPods()
//         requestData()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }

//     private fun requestData(){

//         //Now Playing Movie
//         mMovieModel.getNowPlayingMovies{
//             showErrorMessage()
//         }?.observe(this) {
//             mBannerAdapter.setNewData(it)
//         }


//         //Popular Movie
//         mMovieModel.getPopularMovies {
//             showErrorMessage()
//         }?.observe(this) {
//             mBestPopularMovieListViewPod.setData(it)
//         }


//         //Top Rated Movie
//         mMovieModel.getTopRatedMovies {
//             showErrorMessage()
//         }?.observe(this) {
//             mShowcaseAdapter.setNewData(it)
//         }


//         // Get Genres
//         mMovieModel.getGenres(
//             onSuccess = {
//                 mGenres = it
//                 setUpGenreTabLayout(it)

//                 // Get Movies By Genre For First Genre
//                 it.firstOrNull()?.id?.let { genreId ->
//                     getMoviesByGenre(genreId)
//                 }
//             },
//             onFailure = {
//                 Toast.makeText(this, "Genre Section Failed", Toast.LENGTH_SHORT).show()
//             }
//         )

//         // Actors
//         mMovieModel.getActors(
//             onSuccess = {
//                 mActorListViewPod.setData(it)
//             },
//             onFailure = {
//                 Toast.makeText(this, "Actors Section Failed", Toast.LENGTH_SHORT).show()
//             }
//         )
//     }


//     // to get Genre Movie ID
//     private fun getMoviesByGenre(genreId : Int){
//         mMovieModel.getMoviesByGenre(
//             genreId = genreId.toString(),
//             onSuccess = {
//                 mMoviesByGenreViewPod.setData(it)
//             },
//             onFailure = {
//                 Toast.makeText(this, "Movie By Genre Failed", Toast.LENGTH_SHORT).show()
//             }
//         )
//     }


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

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
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

//    override fun onTapMovieFromBanner(movieId : Int) {
//        startActivity(MovieDetailsActivity.newIntent(this,movieId))
//        Snackbar.make(window.decorView, "Tapped Movie From Banner", Snackbar.LENGTH_LONG).show()
//    }
//
//    override fun onTapMovieFromShowcase(movieId: Int) {
//        startActivity(MovieDetailsActivity.newIntent(this, movieId))
//        Snackbar.make(window.decorView, "Tapped Movie From Showcase", Snackbar.LENGTH_LONG).show()
//    }
//
//    override fun onTapMovie(movieId: Int) {
//        startActivity(MovieDetailsActivity.newIntent(this, movieId))
//        Snackbar.make(window.decorView, "Tapped Movie From Movie", Snackbar.LENGTH_LONG).show()
//    }

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

        private fun showErrorMessage() {
            Toast.makeText(this,"Network Failed",Toast.LENGTH_LONG).show()
    }

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
        startActivity(MovieDetailsActivity.newIntent(this,movieId))
    }

    override fun showError(errorString: String) {
        Snackbar.make(window.decorView, "Movie Network Error", Snackbar.LENGTH_LONG).show()
    }
}
