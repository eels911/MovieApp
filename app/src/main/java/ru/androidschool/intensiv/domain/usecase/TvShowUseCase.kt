package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.TvShow
import ru.androidschool.intensiv.domain.repository.TvShowsRepository

class TvShowUseCase(private val repository: TvShowsRepository) {
    fun getShows(): Single<List<TvShow>>{
        return repository.getShows()
            .applySchedulers()
    }
}