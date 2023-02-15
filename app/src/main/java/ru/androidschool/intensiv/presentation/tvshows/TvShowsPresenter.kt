package ru.androidschool.intensiv.presentation.tvshows

import ru.androidschool.intensiv.data.vo.TvShow
import ru.androidschool.intensiv.domain.usecase.TvShowUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class TvShowsPresenter(private val useCase: TvShowUseCase) :
    BasePresenter<TvShowsPresenter.ShowView>() {

    fun getShows(){
        useCase.getShows()
            .subscribe({ it ->
                view?.showShows(it)
                }, { error ->
                // Логируем ошибку
                Timber.tag(TvShowsFragment.TAG).e(error.toString())
            })
    }


    interface ShowView{
        fun showShows(shows: List<TvShow>)

    }
}