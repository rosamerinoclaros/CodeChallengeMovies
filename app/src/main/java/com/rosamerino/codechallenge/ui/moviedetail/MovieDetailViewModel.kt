package com.rosamerino.codechallenge.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosamerino.codechallenge.data.model.MovieDetail
import com.rosamerino.codechallenge.data.remote.MovieApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
    @Inject
    constructor(
        private val movieApi: MovieApi,
    ) : ViewModel() {
        private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
        val movieDetail: StateFlow<MovieDetail?> = _movieDetail

        fun loadMovieDetail(movieId: Int) {
            viewModelScope.launch {
                _movieDetail.value = movieApi.fetchMovieDetails(movieId)
            }
        }
    }
