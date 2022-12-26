package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("name")
    val name: String
) {
    @SerializedName("poster_path")
    var banner: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
