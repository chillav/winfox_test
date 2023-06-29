package com.example.winfox_test.movies

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    val imageUrl: String,
    val summary: String,
)
