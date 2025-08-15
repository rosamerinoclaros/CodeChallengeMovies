package com.rosamerino.codechallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.ui.movieslist.MovieListItem
import com.rosamerino.codechallenge.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testMovie =
        Movie(
            id = 123,
            title = "Awesome Test Movie",
            overview = "An overview",
            posterUrl = "/path.jpg",
            releaseDate = "2024-01-01",
        )

    @Test
    fun movieListItem_displaysCorrectTitle() {
        composeTestRule.setContent {
            AppTheme {
                MovieListItem(movie = testMovie, onMovieClick = {})
            }
        }

        composeTestRule.onNodeWithText("Awesome Test Movie").assertIsDisplayed()
    }

    @Test
    fun movieListItem_callsOnMovieClick_whenClicked() {
        var clickedMovieId: Int? = null
        val expectedMovieId = testMovie.id

        composeTestRule.setContent {
            AppTheme {
                MovieListItem(
                    movie = testMovie,
                    onMovieClick = { id ->
                        clickedMovieId = id
                    },
                )
            }
        }

        composeTestRule.onNodeWithTag("MovieListItemRow_${testMovie.id}").performClick()

        assert(clickedMovieId == expectedMovieId) {
            "onMovieClick was called with ID $clickedMovieId, but expected $expectedMovieId"
        }
    }

    @Test
    fun movieListItem_dividerIsPresent() {
        composeTestRule.setContent {
            AppTheme {
                MovieListItem(movie = testMovie, onMovieClick = {})
            }
        }
        composeTestRule.onNodeWithTag("MovieListItemRow_${testMovie.id}").assertIsDisplayed()
    }
}
