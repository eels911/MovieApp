package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    var page: Int,
    var results: List<TvShow>,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)
