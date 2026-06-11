package com.example.stayout.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewPropertyNoFeature
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyCardTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun displaysPropertyName() {
        rule.setContent {
            StayScoutTheme {
                PropertyCard(property = previewProperty, onClick = {})
            }
        }

        rule.onNodeWithText(previewProperty.name).assertIsDisplayed()
    }

    @Test
    fun showsFeaturedBadgeWhenPropertyIsFeatured() {
        rule.setContent {
            StayScoutTheme {
                PropertyCard(property = previewProperty, onClick = {})
            }
        }

        rule.onNodeWithText("Featured").assertIsDisplayed()
    }

    @Test
    fun hidesFeaturedBadgeWhenPropertyIsNotFeatured() {
        rule.setContent {
            StayScoutTheme {
                PropertyCard(property = previewPropertyNoFeature, onClick = {})
            }
        }

        rule.onNodeWithText("Featured").assertIsNotDisplayed()
    }

    @Test
    fun invokesOnClickWhenCardIsTapped() {
        var clicked = false
        rule.setContent {
            StayScoutTheme {
                PropertyCard(property = previewProperty, onClick = { clicked = true })
            }
        }

        rule.onNodeWithText(previewProperty.name).performClick()

        assertTrue(clicked)
    }
}
