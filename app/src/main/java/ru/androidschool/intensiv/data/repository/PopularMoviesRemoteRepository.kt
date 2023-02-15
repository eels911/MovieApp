package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.mapper.MovieMapper

class PopularMoviesRemoteRepository : MoviesRepository {

    override fun getMovies(): Single<List<Movie>> {
        return MovieApiClient.apiClient.getPopularMovies()
            .map { MovieMapper.toValueObject(it) }
    }
}