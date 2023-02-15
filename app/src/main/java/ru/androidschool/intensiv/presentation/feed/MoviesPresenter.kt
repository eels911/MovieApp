package ru.androidschool.intensiv.presentation.feed

import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.PopularMovieUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class MoviesPresenter(private val useCase: PopularMovieUseCase) :
    BasePresenter<MoviesPresenter.ShowView>() {

    fun getPopularMovies(){
        useCase.getPopularMovies()
            .subscribe({ it ->
                view?.showPopularMovies(it)
                }, { error ->
                // Логируем ошибку
                Timber.tag(FeedFragment.TAG).e(error.toString())
            })
    }


    interface ShowView{
        fun showPopularMovies(shows: List<Movie>)

    }
}