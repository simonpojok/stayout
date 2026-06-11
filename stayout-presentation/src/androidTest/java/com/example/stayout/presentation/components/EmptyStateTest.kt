package com.example.stayout.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyStateTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun displaysTitleText() {
        rule.setContent {
            StayScoutTheme {
                EmptyState(title = "No results", message = "Try a different search term.")
            }
        }

        rule.onNodeWithText("No results").assertIsDisplayed()
    }

    @Test
    fun displaysMessageText() {
        rule.setContent {
            StayScoutTheme {
                EmptyState(title = "No results", message = "Try a different search term.")
            }
        }

        rule.onNodeWithText("Try a different search term.").assertIsDisplayed()
    }
}
