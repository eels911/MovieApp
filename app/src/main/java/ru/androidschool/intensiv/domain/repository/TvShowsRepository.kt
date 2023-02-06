package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.TvShow

interface TvShowsRepository {
    fun getShows(): Single<List<TvShow>>
}