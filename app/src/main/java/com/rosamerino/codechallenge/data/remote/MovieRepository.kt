package com.rosamerino.codechallenge.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rosamerino.codechallenge.data.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
    @Inject
    constructor(
        private val movieApi: MovieApi,
    ) {
        fun getMoviesStream(query: String): Flow<PagingData<Movie>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = { MoviePagingSource(movieApi, query) },
            ).flow
    }
