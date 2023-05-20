//package com.example.themovieapp.network.dataagents
//
//import android.os.AsyncTask
//import androidx.loader.content.AsyncTaskLoader
//import com.example.themovieapp.network.responses.MovieListResponse
//import com.example.themovieapp.utils.API_GET_NOW_PLAYING
//import com.example.themovieapp.utils.BASE_URL
//import com.example.themovieapp.utils.MOVIE_API_KEY
//import com.google.gson.Gson
//import okhttp3.OkHttpClient
//import java.sql.Time
//import java.util.concurrent.TimeUnit
//
//object OkHttpDataAgentImpl : MovieDataAgent {
//
//    private val mClient : OkHttpClient = OkHttpClient.Builder()
//        .connectTimeout(15,TimeUnit.SECONDS)
//        .readTimeout(15,TimeUnit.SECONDS)
//        .writeTimeout(15,TimeUnit.SECONDS)
//        .build()
//
//    override fun getNowPlayingMovies() {
//        GetNowPlayingMovieOkHttpTask(mClient).execute()
//    }
//
//    class GetNowPlayingMovieOkHttpTask(
//        private val mOkHttpClient: OkHttpClient
//    ) : AsyncTask<Void,Void,MovieListResponse>(){
//
//        override fun doInBackground(vararg params: Void?): MovieListResponse? {
//
//            //build req obj
//            val request = okhttp3.Request.Builder()
//                .url("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
//                .build()
//
//            try {
//                val response = mOkHttpClient.newCall(request).execute()         // connect to Url and get back "response"
//
//                if(response.isSuccessful) {
//                    response.body?. let {
//                        val responseString = it.string()
//                        val response = Gson().fromJson(
//                            responseString,
//                            MovieListResponse::class.java
//                        )
//                        return response
//                    }
//                }
//
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: MovieListResponse?) {
//            super.onPostExecute(result)
//        }
//    }
//}