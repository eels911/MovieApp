package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class ActorsDto (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String?,
)