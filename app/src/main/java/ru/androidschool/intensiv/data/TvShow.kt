package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShow (
    @SerializedName("poster_path")
    val banner: String,
    @SerializedName("vote_average")
    val rating: String,
    @SerializedName("name")
    val name: String
        )
