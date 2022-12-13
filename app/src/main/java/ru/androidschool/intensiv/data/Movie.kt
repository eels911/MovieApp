package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

//class Movie(
//    var title: String? = "",
//    var voteAverage: Double = 0.0
//) {
//    val rating: Float
//        get() = voteAverage.div(2).toFloat()
//}

data class Movie(
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("overview")
    val overview: String?,

)
