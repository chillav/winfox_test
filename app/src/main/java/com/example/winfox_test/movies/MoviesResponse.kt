package com.example.winfox_test.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val movies: List<MovieResponse>?,
)

data class MovieResponse(
    @SerializedName("display_title")
    val title: String?,
    @SerializedName("multimedia")
    val image: MovieImage?,
    @SerializedName("summary_short")
    val summary: String?,
)

data class MovieImage(
    @SerializedName("src")
    val url: String?,
)
