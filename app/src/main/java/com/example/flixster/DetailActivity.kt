package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers

private const val TAG = "DetailActivity"
private const val YOUTUBE_API_KEY = "AIzaSyAm3rOBVM6spl4OlNSd8o-7LOjEGKMIPI4"
private const val TRAILERS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class DetailActivity : YouTubeBaseActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var ratingBar: TextView
    private lateinit var ytPlayerView: YouTubePlayerView
    private lateinit var release_date: TextView
    private lateinit var popular: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.rbVoteAverage)
        //ytPlayerView = findViewById(R.id.player)
        release_date = findViewById(R.id.release_date)
        popular = findViewById(R.id.popular)


        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")
        val tit :String = movie.title
        val over :String = movie.overview
        val rat :String = movie.movieId.toString()
        val rel :String =  movie.release.toString()
        val pop:String = movie.popular.toString()




        tvTitle.text = ("Name: $tit")
        tvOverview.text = ("Symbol: $over")
        ratingBar.text = ("Current Price: $rat") // get rating of movie
        release_date.text = ("Highest Price in 24hrs: $rel")
        popular.text = ("Price Change in 24hrs: $pop")

        val client = AsyncHttpClient()
        client.get(TRAILERS_URL.format(movie.movieId), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess $statusCode")

                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0){
                    Log.w(TAG, "No movie trailers found")
                    return
                }
                val movieTrailerJSON = results.getJSONObject(0)
                val youtubeKey = movieTrailerJSON.getString("key")

            }
        })
    }


}