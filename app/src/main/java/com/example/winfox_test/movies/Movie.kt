package com.example.winfox_test.movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val imageUrl: String,
    val summary: String,
) : Parcelable
