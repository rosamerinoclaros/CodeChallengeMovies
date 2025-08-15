package com.rosamerino.codechallenge.moviedetail

import app.cash.turbine.test
import com.rosamerino.codechallenge.data.remote.MovieApi
import com.rosamerino.codechallenge.ui.moviedetail.MovieDetailViewModel
import com.rosamerino.codechallenge.util.TestModels
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {
    private val movieApi: MovieApi = mockk()
    private val testMovieDetail = TestModels.createMovieDetail()

    @Test
    fun `loadMovieDetail loads MovieDetail`() =
        runTest {
            coEvery { movieApi.fetchMovieDetails(1) } returns testMovieDetail
            val viewModel = MovieDetailViewModel(movieApi)

            viewModel.movieDetail.test {
                assertEquals(null, awaitItem())
                viewModel.loadMovieDetail(1)
                assertEquals(testMovieDetail, awaitItem())
            }
        }
}
