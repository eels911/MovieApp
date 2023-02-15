package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class PopularMovieUseCase(private val repository: MoviesRepository) {
    fun getPopularMovies(): Single<List<Movie>>{
        return repository.getMovies()
            .applySchedulers()
    }
}