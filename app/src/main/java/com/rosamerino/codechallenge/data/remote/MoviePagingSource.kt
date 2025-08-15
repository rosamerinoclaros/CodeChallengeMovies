package com.rosamerino.codechallenge.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rosamerino.codechallenge.data.model.Movie

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val query: String,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response =
                if (query.isBlank()) {
                    // Fetch popular movies if query is blank
                    movieApi.fetchMovies(page = page)
                } else {
                    // Fetch movies based on the search query
                    movieApi.searchMovies(query = query, page = page)
                }
            val totalPages = response.totalPages
            val movies = response.results.map { it.toMovie() }
            val nextKey = if (page < totalPages) page + 1 else null
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
}
