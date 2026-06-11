package com.example.stayout.presentation.home.sections.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileSectionTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `displays primary button section header`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Primary Button").assertIsDisplayed()
    }

    @Test
    fun `displays tonal button section header`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Tonal Button").assertIsDisplayed()
    }

    @Test
    fun `displays outlined button section header`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Outlined Button").assertIsDisplayed()
    }

    @Test
    fun `displays text button section header`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Text Button").assertIsDisplayed()
    }
}
