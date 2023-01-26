package ru.androidschool.intensiv.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.GenreResponse
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.data.TvShowsResponse

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlayingMovie(@Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String): Single<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Single<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Single<MoviesResponse>

    @GET("tv/popular")
    fun getPopularSeries(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): Single<TvShowsResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetailsResponse>

    @GET("genre/movie/list")
    fun getGenres(): Single<GenreResponse>

    @GET("search/movie")
    fun searchByQuery(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("query") query: String): Single<MoviesResponse>

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}
