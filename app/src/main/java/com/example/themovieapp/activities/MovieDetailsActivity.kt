package com.example.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.themovieapp.R
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.databinding.ActivityMovieDetailsBinding
import com.example.themovieapp.mvp.presenters.MovieDetailsPresenter
import com.example.themovieapp.mvp.presenters.MovieDetailsPresenterImpl
import com.example.themovieapp.mvp.views.MovieDetailView
import com.example.themovieapp.utils.IMAGE_BASE_URL
import com.example.themovieapp.viewpods.ActorListsViewPod
import com.google.android.material.snackbar.Snackbar

class MovieDetailsActivity : AppCompatActivity() , MovieDetailView{

    //create new instance
    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context : Context,movieId : Int) : Intent {
            val intent = Intent(context,MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID,movieId)
            return intent
        }
    }

    private lateinit var binding : ActivityMovieDetailsBinding

    //view pod references (cast and crew)
    private lateinit var mActorsListsViewPod : ActorListsViewPod
    private lateinit var mCreatorsListsViewPod : ActorListsViewPod

    // Presenter
    private lateinit var mPresenter : MovieDetailsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPresenter()

        setUpViewPods()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID,0)
        movieId?.let {
            mPresenter.onUiReadyInMovieDetails(this,movieId)
        }
    }


    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MovieDetailsPresenterImpl::class.java]
        mPresenter.initView(this)
    }


//    private fun requestData(movieId: Int){
//        //Movie Detail
//        mMovieModel.getMovieDetails(
//            movieId = movieId.toString(),
//            onFailure = {
//                showErrorMessage()
//            })?.observe(this) {
//                it?.let { movieDetails -> bindData(movieDetails) }
//        }
//
//
//        // Credit Movie (cast and crew)
//        mMovieModel.getCreditsByMovie(
//            movieId = movieId.toString(),
//            onSuccess = {
//                mActorsListsViewPod.setData(it.first)
//                mCreatorsListsViewPod.setData(it.second)
//            },
//            onFailure = {
//                Log.e("Error Message",it.toString())
//                Toast.makeText(this, "Credits Failed $it", Toast.LENGTH_SHORT).show()
//            }
//        )
//    }


    private fun setUpListeners(){
        binding.btnBack.setOnClickListener{
            super.onBackPressed()
        }
    }

    private fun setUpViewPods(){
        mActorsListsViewPod = binding.vpActors.root
        mActorsListsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )

        mCreatorsListsViewPod = binding.vpCreators.root
        mCreatorsListsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }

    private fun bindData(movie : MovieVO){
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMovieDetails)

        binding.tvMovieName.text = movie.title ?: ""
        binding.tvReleaseYear.text = movie.releaseDate?.substring(0,3)
        binding.tvRating.text = movie.voteAverage?.toString() ?: ""
        movie.voteCount?.let {
            binding.tvNumberOfVotes.text = "$it VOTES"
        }

        binding.rbRatingMovieDetails.rating = movie.getRatingBarBaseOnFiveStars()

        bindGenres(movie, movie.genres ?: listOf())

        binding.tvOverview.text = movie.overview ?: ""
        binding.tvOriginalTitle.text = movie.originalTitle ?: ""
        binding.tvType.text = movie.getGenresAsCommaSeparatedString()
        binding.tvProduction.text = movie.getCountriesAsCommaSeparatedString()
        binding.tvPremiere.text = movie.releaseDate ?: ""
        binding.tvDescription.text = movie.overview ?: ""
    }

    private fun bindGenres(
        movie : MovieVO,
        genreList : List<GenreVO>
    ) {
        movie.genres?.count()?.let {

            binding.tvFirstGenre.text = genreList.firstOrNull()?.name ?: ""
            binding.tvSecondGenre.text = genreList.getOrNull(1)?.name ?: ""
            binding.tvThirdGenre.text = genreList.getOrNull(2)?.name ?: ""

            if (it < 2){
                binding.tvSecondGenre.visibility = View.GONE
                binding.tvThirdGenre.visibility = View.GONE
            }
            else if(it < 3){
                binding.tvThirdGenre.visibility = View.GONE
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this,"Movie Detail Failed", Toast.LENGTH_LONG).show()
    }

    override fun showMovieDetails(movie: MovieVO) {
        bindData(movie)
    }

    override fun showCreditsByMovie(cast: List<ActorVO>, crew: List<ActorVO>) {
        mActorsListsViewPod.setData(cast)
        mCreatorsListsViewPod.setData(crew)
    }

    override fun navigateBack() {
        finish()
    }

    override fun showError(errorString: String) {
        Snackbar.make(window.decorView, "Movie Details Network Error", Snackbar.LENGTH_LONG).show()

    }
}