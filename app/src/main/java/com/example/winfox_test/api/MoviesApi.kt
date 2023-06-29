package com.example.winfox_test.api

import com.example.winfox_test.Constants.API_KEY
import com.example.winfox_test.movies.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("reviews/all.json")
    suspend fun getMovies(
        @Query("api-key") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0,
    ): Response<MoviesResponse>
}
