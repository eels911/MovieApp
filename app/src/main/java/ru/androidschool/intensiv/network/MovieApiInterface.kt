package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.GenreResponse
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.data.TvShowsResponse

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlayingMovie(@Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Call<MoviesResponse>

    @GET("tv/popular")
    fun getPopularSeries(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Call<TvShowsResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Call<MovieDetailsResponse>

    @GET("genre/movie/list")
    fun getGenres(): Call<GenreResponse>

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}
