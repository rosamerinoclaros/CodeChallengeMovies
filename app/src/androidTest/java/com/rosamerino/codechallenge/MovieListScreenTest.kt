package com.rosamerino.codechallenge

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.ui.movieslist.MovieListScreen
import com.rosamerino.codechallenge.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testMovie1 = Movie(1, "Movie One", "", "", "")
    private val testMovie2 = Movie(2, "Movie Two", "", "", "")

    @Test
    fun movieListScreen_displaysSearchInputAndInitialMovies() {
        val moviesFlow = MutableStateFlow(PagingData.from(listOf(testMovie1, testMovie2)))
        val searchQuery = mutableStateOf("")

        composeTestRule.setContent {
            AppTheme {
                val lazyPagingItems = moviesFlow.collectAsLazyPagingItems()
                MovieListScreen(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = { searchQuery.value = it },
                    onMovieClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Search").assertIsDisplayed() // Label von SearchInputField
        composeTestRule.onNodeWithText("Movie One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Movie Two").assertIsDisplayed()
    }

    @Test
    fun movieListScreen_searchUpdatesQuery_andCallsOnSearchQueryChanged() {
        val moviesFlow = MutableStateFlow(PagingData.from(listOf(testMovie1)))
        val searchQuery = mutableStateOf("")
        var onQueryChangedCalledWith: String? = null

        composeTestRule.setContent {
            AppTheme {
                val lazyPagingItems = moviesFlow.collectAsLazyPagingItems()
                MovieListScreen(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = {
                        searchQuery.value = it
                        onQueryChangedCalledWith = it
                    },
                    onMovieClick = {},
                )
            }
        }
        val newQuery = "Test Query"
        composeTestRule
            .onNodeWithText("Search")
            .performTextInput(newQuery)

        composeTestRule.runOnIdle {
            assert(searchQuery.value == newQuery)
            assert(onQueryChangedCalledWith == newQuery)
        }
    }

    @Test
    fun movieListScreen_callsOnMovieClick_whenMovieItemIsClicked() {
        val moviesFlow = MutableStateFlow(PagingData.from(listOf(testMovie1)))
        val searchQuery = mutableStateOf("")
        var clickedMovieId: Int? = null

        composeTestRule.setContent {
            AppTheme {
                val lazyPagingItems = moviesFlow.collectAsLazyPagingItems()
                MovieListScreen(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = { searchQuery.value = it },
                    onMovieClick = { id -> clickedMovieId = id },
                )
            }
        }

        composeTestRule.onNodeWithTag("MovieListItemRow_${testMovie1.id}").performClick()

        composeTestRule.runOnIdle {
            assert(clickedMovieId == testMovie1.id)
        }
    }

    @Test
    fun movieListScreen_showsNoMoviesFound_forEmptySearch() {
        val emptyMoviesFlow = MutableStateFlow(PagingData.empty<Movie>())

        val searchQuery = mutableStateOf("NonExistentMovie")

        composeTestRule.setContent {
            AppTheme {
                val lazyPagingItems = emptyMoviesFlow.collectAsLazyPagingItems()
                MovieListScreen(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = { searchQuery.value = it },
                    onMovieClick = {},
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Keine Filme für '${searchQuery.value}' gefunden.", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun movieListScreen_showsGenericNoMovies_whenNoSearchAndEmpty() {
        val emptyMoviesFlow = MutableStateFlow(PagingData.empty<Movie>())
        val searchQuery = mutableStateOf("")

        composeTestRule.setContent {
            AppTheme {
                val lazyPagingItems = emptyMoviesFlow.collectAsLazyPagingItems()
                MovieListScreen(
                    lazyPagingItems = lazyPagingItems,
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = { searchQuery.value = it },
                    onMovieClick = {},
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Keine Filme verfügbar. Versuchen Sie eine Suche!", useUnmergedTree = true).assertIsDisplayed()
    }
}
