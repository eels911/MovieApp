package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("title") val title: String
)
