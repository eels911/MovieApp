package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.vo.TvShow
import ru.androidschool.intensiv.domain.repository.TvShowsRepository
import ru.androidschool.intensiv.mapper.TvShowMapper

class TvShowRemoteRepository: TvShowsRepository {
    override fun getShows(): Single<List<TvShow>> {
       return MovieApiClient.apiClient.getPopularSeries()
           .map { TvShowMapper.toValueObject(it) }
    }
}