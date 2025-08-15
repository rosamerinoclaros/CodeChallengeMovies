package com.rosamerino.codechallenge

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.rosamerino.codechallenge.ui.movieslist.SearchInputField
import org.junit.Rule
import org.junit.Test

class SearchInputFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val searchInputFieldTag = "SearchInputFieldTag"

    @Test
    fun searchInputField_displaysInitialQuery() {
        val initialQuery = "Initial Search"
        composeTestRule.setContent {
            SearchInputField(
                query = initialQuery,
                onQueryChange = {},
                modifier = Modifier.testTag(searchInputFieldTag),
            )
        }

        composeTestRule.onNodeWithText(initialQuery).assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }

    @Test
    fun searchInputField_callsOnQueryChange_whenTextIsEntered() {
        var latestQuery = ""
        val newText = "Batman"

        composeTestRule.setContent {
            val queryState = remember { mutableStateOf("") }
            SearchInputField(
                query = queryState.value,
                onQueryChange = {
                    latestQuery = it
                    queryState.value = it
                },
                modifier = Modifier.testTag(searchInputFieldTag),
            )
        }
        composeTestRule.onNodeWithTag(searchInputFieldTag).performTextInput(newText)

        assert(latestQuery == newText) { "onQueryChange was not called with the correct text. Expected: $newText, Got: $latestQuery" }

        composeTestRule.onNodeWithText(newText).assertIsDisplayed()
    }

    @Test
    fun searchInputField_labelIsDisplayed() {
        composeTestRule.setContent {
            SearchInputField(
                query = "",
                onQueryChange = {},
                modifier = Modifier.testTag(searchInputFieldTag),
            )
        }
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }
}
