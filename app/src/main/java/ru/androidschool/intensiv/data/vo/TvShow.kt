package ru.androidschool.intensiv.data.vo

data class TvShow(
    val rating: Double,
    val name: String
) {
    var banner: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}