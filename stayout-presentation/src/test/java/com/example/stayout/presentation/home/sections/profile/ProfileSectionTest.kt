package com.example.stayout.presentation.home.sections.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w480dp-h4000dp-port-xhdpi")
class ProfileSectionTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `displays profile title`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Profile").assertIsDisplayed()
    }

    @Test
    fun `displays coming soon label`() {
        rule.setContent {
            StayScoutTheme {
                ProfileSection()
            }
        }

        rule.onNodeWithText("Coming soon").assertIsDisplayed()
    }
}
