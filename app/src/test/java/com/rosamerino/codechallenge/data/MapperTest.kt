package com.rosamerino.codechallenge.data

import com.rosamerino.codechallenge.data.remote.toMovie
import com.rosamerino.codechallenge.data.remote.toMovieDetail
import com.rosamerino.codechallenge.util.TestModels
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {
    @Test
    fun `MovieApiModel is mapped correctly to Movie`() {
        val apiModel = TestModels.createMovieApiModel()
        val movie = apiModel.toMovie()
        assertEquals(1, movie.id)
        assertEquals("Title", movie.title)
        assertEquals("This is a test movie overview.", movie.overview)
        assertEquals("/poster123.jpg", movie.posterUrl)
        assertEquals("2024-01-15", movie.releaseDate)
    }

    @Test
    fun `MovieDetailApiModel is mapped correctly to MovieDetail`() {
        val apiModel = TestModels.createMovieDetailApiModel()
        val detail = apiModel.toMovieDetail()
        assertEquals(listOf("Action", "Sci-Fi"), detail.genres)
        assertEquals(120, detail.runtime)
    }
}
