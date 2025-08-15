package com.rosamerino.codechallenge.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.data.remote.MovieRepository
import com.rosamerino.codechallenge.ui.movieslist.MovieListViewModel
import com.rosamerino.codechallenge.util.TestModels
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieListViewModel
    private val repository: MovieRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val testMovie = TestModels.createMovie()
    private val testPagingData: PagingData<Movie> = PagingData.from(listOf(testMovie))
    private val emptyPagingData: PagingData<Movie> = PagingData.empty()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getMoviesStream(any()) } returns flowOf(testPagingData)

        viewModel = MovieListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChanged updates searchQuery StateFlow`() =
        runTest(testDispatcher) {
            val newQuery = "Batman"
            viewModel.onSearchQueryChanged(newQuery)
            assertEquals(newQuery, viewModel.searchQuery.value)
        }

    @Test
    fun `movies flow emits PagingData when searchQuery changes`() =
        runTest(testDispatcher) {
            coEvery { repository.getMoviesStream("") } returns flowOf(emptyPagingData)
            val viewModel = MovieListViewModel(repository)
            viewModel.onSearchQueryChanged("initial")

            testDispatcher.scheduler.advanceUntilIdle()

            val newQuery = "Superman"
            coEvery { repository.getMoviesStream(newQuery) } returns flowOf(testPagingData)

            viewModel.onSearchQueryChanged(newQuery)
            assertEquals(newQuery, viewModel.searchQuery.value)

            testDispatcher.scheduler.advanceTimeBy(501)
            testDispatcher.scheduler.advanceUntilIdle()

            val anotherQuery = "Joker"
            coEvery { repository.getMoviesStream(anotherQuery) } returns flowOf(testPagingData)

            viewModel.onSearchQueryChanged(anotherQuery)
            assertEquals(anotherQuery, viewModel.searchQuery.value)
        }
}
