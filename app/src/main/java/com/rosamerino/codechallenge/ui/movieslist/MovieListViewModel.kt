package com.rosamerino.codechallenge.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieListViewModel
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

        val movies: Flow<PagingData<Movie>> =
            searchQuery
                .debounce(500L) // Wait for 500ms after the last change
                .distinctUntilChanged() // Only react to distinct queries
                .flatMapLatest { query ->
                    repository.getMoviesStream(query)
                }.cachedIn(viewModelScope)

        fun onSearchQueryChanged(query: String) {
            _searchQuery.value = query
        }
    }