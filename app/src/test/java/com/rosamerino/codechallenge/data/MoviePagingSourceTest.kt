package com.rosamerino.codechallenge.data

import androidx.paging.PagingSource
import com.rosamerino.codechallenge.data.model.*
import com.rosamerino.codechallenge.data.remote.*
import com.rosamerino.codechallenge.util.TestModels
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MoviePagingSourceTest {
    private val movieApi = mockk<MovieApi>()

    @Test
    fun `load returns a page with movies`() =
        runBlocking {
            val movies = listOf(TestModels.createMovieApiModel())
            val response = TestModels.createMovieListResponse(1, movies, 2)
            coEvery { movieApi.fetchMovies(page = 1) } returns response

            val pagingSource = MoviePagingSource(movieApi, "")
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(1, 20, false))

            assertTrue(result is PagingSource.LoadResult.Page)
            val page = result as PagingSource.LoadResult.Page
            assertEquals(1, page.data.size)
            assertNull(page.prevKey)
            assertEquals(2, page.nextKey)
        }

    @Test
    fun `load returns an error if an exception is thrown`() =
        runBlocking {
            coEvery { movieApi.fetchMovies(page = 1) } throws RuntimeException("Error")
            val pagingSource = MoviePagingSource(movieApi, "")
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(1, 20, false))
            assertTrue(result is PagingSource.LoadResult.Error)
        }
}
