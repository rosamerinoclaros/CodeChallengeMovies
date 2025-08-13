package com.rosamerino.codechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.rosamerino.codechallenge.navigation.AppNavHost
import com.rosamerino.codechallenge.ui.moviedetail.MovieDetailViewModel
import com.rosamerino.codechallenge.ui.movieslist.MovieListViewModel
import com.rosamerino.codechallenge.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val movieListViewModel: MovieListViewModel by viewModels()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val lazyPagingItems = movieListViewModel.movies.collectAsLazyPagingItems()
                val searchQuery by movieListViewModel.searchQuery.collectAsState()
                val movieDetail by movieDetailViewModel.movieDetail.collectAsState()
                AppNavHost(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery,
                    searchQueryChanged = movieListViewModel::onSearchQueryChanged,
                    loadMovieDetail = movieDetailViewModel::loadMovieDetail,
                    movieDetail = movieDetail,
                )
            }
        }
    }
}
