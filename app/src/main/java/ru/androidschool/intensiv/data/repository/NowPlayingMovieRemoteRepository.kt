package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class NowPlayingMovieRemoteRepository: MoviesRepository {
    override fun getMovies(): Single<List<Movie>> {
        TODO("Not yet implemented")
    }
}