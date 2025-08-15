package com.rosamerino.codechallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.data.model.MovieDetail
import com.rosamerino.codechallenge.ui.moviedetail.MovieDetailScreen
import com.rosamerino.codechallenge.ui.movieslist.MovieListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    lazyPagingItems: LazyPagingItems<Movie>,
    searchQuery: String,
    searchQueryChanged: (String) -> Unit,
    loadMovieDetail: (Int) -> Unit,
    movieDetail: MovieDetail?,
) {
    NavHost(
        navController = navController,
        startDestination = "movieList",
    ) {
        composable("movieList") {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movieDetail/$movieId")
                },
                lazyPagingItems = lazyPagingItems,
                searchQuery = searchQuery,
                onSearchQueryChanged = searchQueryChanged,
            )
        }
        composable("movieDetail/{movieId}") { backStackEntry ->
            val movieId =
                backStackEntry.arguments
                    ?.getString("movieId")
                    ?.toIntOrNull() ?: return@composable

            LaunchedEffect(movieId) { loadMovieDetail(movieId) }
            movieDetail?.let { MovieDetailScreen(it) }
        }
    }
}
