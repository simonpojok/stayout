package com.example.stayout.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencySelectorTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun displaysAllThreeCurrencyLabels() {
        rule.setContent {
            StayScoutTheme {
                CurrencySelector(
                    selectedCurrency = CurrencyDomainModel.EUR,
                    onCurrencySelect = {},
                )
            }
        }

        rule.onNodeWithText("EUR").assertIsDisplayed()
        rule.onNodeWithText("USD").assertIsDisplayed()
        rule.onNodeWithText("GBP").assertIsDisplayed()
    }

    @Test
    fun invokesCallbackWithCorrectCurrencyWhenTabTapped() {
        var selected: CurrencyDomainModel? = null
        rule.setContent {
            StayScoutTheme {
                CurrencySelector(
                    selectedCurrency = CurrencyDomainModel.EUR,
                    onCurrencySelect = { selected = it },
                )
            }
        }

        rule.onNodeWithText("USD").performClick()

        assertEquals(CurrencyDomainModel.USD, selected)
    }

    @Test
    fun defaultsToShowingSelectedCurrencyLabel() {
        rule.setContent {
            StayScoutTheme {
                CurrencySelector(
                    selectedCurrency = CurrencyDomainModel.GBP,
                    onCurrencySelect = {},
                )
            }
        }

        rule.onNodeWithText("GBP").assertIsDisplayed()
    }
}
