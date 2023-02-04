package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.data.database.MovieEntity

class Movie(

    val id: Int?,
    var title: String?,

) {
    fun convertMovie() = MovieEntity(id, title)
}
