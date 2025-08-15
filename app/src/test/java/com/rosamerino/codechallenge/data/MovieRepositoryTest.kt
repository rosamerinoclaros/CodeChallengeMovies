package com.rosamerino.codechallenge.data

import com.rosamerino.codechallenge.data.remote.MovieApi
import com.rosamerino.codechallenge.data.remote.MovieRepository
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class MovieRepositoryTest {
    private val movieApi = mockk<MovieApi>(relaxed = true)
    private val repository = MovieRepository(movieApi)

    @Test
    fun `getMoviesStream returns PagingData Flow`() =
        runBlocking {
            val flow = repository.getMoviesStream("Batman")
            val pagingData = flow.first()
            assertNotNull(pagingData)
        }
}
