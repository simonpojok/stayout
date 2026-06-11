package com.example.stayout.presentation.home.sections.bookings

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
@Config(sdk = [34])
class BookingsSectionTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `displays coming soon text`() {
        rule.setContent {
            StayScoutTheme {
                BookingsSection()
            }
        }

        rule.onNodeWithText("Bookings — coming soon").assertIsDisplayed()
    }
}
