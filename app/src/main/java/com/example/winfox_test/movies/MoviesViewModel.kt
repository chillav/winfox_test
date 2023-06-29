package com.example.winfox_test.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winfox_test.R
import com.example.winfox_test.api.MoviesApi
import com.example.winfox_test.helpers.ToastHelper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesApi: MoviesApi,
    private val toastHelper: ToastHelper,
) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _isLoading = MutableStateFlow(true)

    val movies = _movies.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                val response = moviesApi.getMovies().body()
                response?.movies?.map {
                    Movie(
                        title = it.title.orEmpty(),
                        imageUrl = it.image?.url.orEmpty(),
                        summary = it.summary.orEmpty()
                    )
                }.orEmpty()
            }.onSuccess {
                _isLoading.value = false
                _movies.value = it
            }.onFailure { error ->
                if (error is CancellationException) throw error
                toastHelper.showToast(textRes = R.string.network_error)
            }
        }
    }
}
