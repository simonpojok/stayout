package com.example.stayout.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorStateTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun displaysErrorMessage() {
        rule.setContent {
            StayScoutTheme {
                ErrorState(
                    message = "No internet connection. Check your network and try again.",
                    onRetry = {},
                )
            }
        }

        rule
            .onNodeWithText("No internet connection. Check your network and try again.")
            .assertIsDisplayed()
    }

    @Test
    fun retryButtonClickInvokesCallback() {
        var retried = false
        rule.setContent {
            StayScoutTheme {
                ErrorState(
                    message = "Something went wrong.",
                    onRetry = { retried = true },
                )
            }
        }

        rule.onNodeWithText("Retry").performClick()

        assertTrue(retried)
    }
}
