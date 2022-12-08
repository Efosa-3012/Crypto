package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize

data class Movie (
    val movieId: Double,
    val voteAverage: String,
    private val posterPath: String,
    val title: String,
    val overview: String,
    val release: Double,
    val popular: Double
        ) : Parcelable
{
    @IgnoredOnParcel
    val posterImageURL = posterPath
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getDouble("current_price"),
                        movieJson.getString("symbol"),
                        movieJson.getString("image"),
                        movieJson.getString("id"),
                        movieJson.getString("symbol"),
                        movieJson.getDouble("high_24h"),
                        movieJson.getDouble("price_change_24h")
                    )
                )
            }
            return movies
        }
    }
}
