package com.example.stayout.presentation.home.sections.saved

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SavedSectionTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `displays coming soon text`() {
        rule.setContent {
            StayScoutTheme {
                SavedSection()
            }
        }

        rule.onNodeWithText("Saved — coming soon").assertIsDisplayed()
    }
}
