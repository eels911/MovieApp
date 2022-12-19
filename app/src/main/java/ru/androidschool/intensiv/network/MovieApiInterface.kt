package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.data.TvShowsResponse

interface MovieApiInterface {
    @GET("movie/now_playing")
    fun getNowPlayingMovie(@Query("api_key") apiKey:String, @Query("language") language:String): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page: Int
    ): Call<MoviesResponse>
    @GET("tv/popular")
    fun getPopularSeries(
        @Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page: Int
    ): Call<TvShowsResponse>


}
