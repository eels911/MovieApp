package ru.androidschool.intensiv.mapper

import ru.androidschool.intensiv.data.dto.TvShowDto
import ru.androidschool.intensiv.data.dto.TvShowsResponseDto
import ru.androidschool.intensiv.data.vo.TvShow

object TvShowMapper {

    fun toValueObject(dto: TvShowsResponseDto): List<TvShow> {
        return dto.results.map { toValueObject(it) }
    }

    private fun toValueObject(dto: TvShowDto): TvShow {
        return TvShow(
            rating = dto.rating,
            name = dto.name
        )
    }

}