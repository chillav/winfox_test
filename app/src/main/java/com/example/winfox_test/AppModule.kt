package com.example.winfox_test

import com.example.winfox_test.Constants.BASE_URL
import com.example.winfox_test.api.MoviesApi
import com.example.winfox_test.helpers.ToastHelper
import com.example.winfox_test.movies.MoviesViewModel
import com.example.winfox_test.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    viewModel { PasswordViewModel(get()) }
    viewModel { MoviesViewModel(get(), get()) }

    single { ToastHelper(get()) }
    single<MoviesApi> { get<Retrofit>().create(MoviesApi::class.java) }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
