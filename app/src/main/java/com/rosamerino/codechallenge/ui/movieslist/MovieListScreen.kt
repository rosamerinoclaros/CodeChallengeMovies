package com.rosamerino.codechallenge.ui.movieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rosamerino.codechallenge.data.model.Movie

@Composable
fun MovieListScreen(
    lazyPagingItems: LazyPagingItems<Movie>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    val paddingTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
                .background(color = MaterialTheme.colorScheme.background),
    ) {
        SearchInputField(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            query = searchQuery,
            onQueryChange = { newQuery -> onSearchQueryChanged(newQuery) },
        )
        Spacer(Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
            items(
                count = lazyPagingItems.itemCount,
            ) { index ->
                val movie = lazyPagingItems[index]
                if (movie != null) {
                    MovieListItem(movie, onMovieClick = onMovieClick)
                }
            }

            lazyPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize()) {
                                CircularProgressIndicator(
                                    modifier =
                                        Modifier
                                            .align(Alignment.Center)
                                            .testTag("LoadingIndicator"),
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            val errorState = loadState.refresh as LoadState.Error
                            Column(
                                modifier = Modifier.fillParentMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text("Error loading movie list: ${errorState.error.localizedMessage}")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            val errorState = loadState.append as LoadState.Error
                            Column(
                                modifier = Modifier.fillParentMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text("Error loading more movies: ${errorState.error.localizedMessage}")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    loadState.refresh is LoadState.NotLoading && itemCount == 0 -> {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                                if (searchQuery.isNotEmpty()) {
                                    Text("No movies found for '$searchQuery'.")
                                } else {
                                    Text("No movies available. Try a search!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(Modifier.padding(bottom = 4.dp), color = MaterialTheme.colorScheme.primary)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { onMovieClick(movie.id) }
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .testTag("MovieListItemRow_${movie.id}"),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .padding(start = 12.dp)
                        .testTag("MovieListItemTitle_${movie.id}"),
            )
        }
    }
}
