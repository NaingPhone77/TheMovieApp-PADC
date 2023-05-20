package com.example.themovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.API_GET_NOW_PLAYING
import com.example.themovieapp.utils.BASE_URL
import com.example.themovieapp.utils.MOVIE_API_KEY
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// we want to make a singleton to MovieDataAgent. So we use (object)***

object MovieDataAgentImpl : MovieDataAgent {

//    fun getExecute(){
//        GetNowPlayingMovieTask().execute()
//    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieTask().execute()
    }

    class GetNowPlayingMovieTask() : AsyncTask<Void, Void, MovieListResponse>() {

        override fun doInBackground(vararg params: Void?): MovieListResponse? {     //doInBackground-> work in Background Thread

            val url: URL

            var reader: BufferedReader? = null
            val stringBuilder: StringBuilder

            try {
                //create HttpURLConnection     (step 1 to 7 Network call)
                url =
                    URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")  // (step 1) three double code means Raw String

                val connection = url.openConnection() as HttpURLConnection   // st-2

                // Set HTTP Method
                connection.requestMethod = "GET"      // st - 3

                // give it 15 sec to respond
                connection.readTimeout = 15 * 1000    // 4  ms

                connection.doInput = true             // 5
                connection.doOutput = false           // 6

                connection.connect()                  // 7


                //read the output from the server
                reader = BufferedReader(
                    InputStreamReader(connection.inputStream)
                )        //8

                stringBuilder = StringBuilder()

                for (line in reader.readLines()) {
                    stringBuilder.append(line + "\n")
                }

                val responseString = stringBuilder.toString()
                Log.d("Now Playing Movie", responseString)

                val movieListResponse = Gson().fromJson(
                    responseString,
                    MovieListResponse::class.java
                )

                return movieListResponse
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NewsError", e.message ?: "")
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (ioe: IOException) {
                        ioe.printStackTrace()
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {     // work in Main Thread
            super.onPostExecute(result)
        }
    }

}