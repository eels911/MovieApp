package ru.androidschool.intensiv.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.dto.GenreDto
import ru.androidschool.intensiv.data.dto.MovieDetailsDto
import ru.androidschool.intensiv.data.dto.MoviesResponseDto
import ru.androidschool.intensiv.data.dto.TvShowsResponseDto

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Single<MoviesResponseDto>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query(PAGE) page: Int
    ): Single<MoviesResponseDto>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    //    @Query(PAGE) page: Int = 1
    ): Single<MoviesResponseDto>

    @GET("tv/popular")
    fun getPopularSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
   //     @Query(PAGE) page: Int
    ): Single<TvShowsResponseDto>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetailsDto>

    @GET("genre/movie/list")
    fun getGenres(): Single<GenreDto>

    @GET("search/movie")
    fun searchByQuery(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("query") query: String): Single<MoviesResponseDto>

    companion object {
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        private const val LANGUAGE = "ru"
        private const val PAGE = "3"
    }
}
