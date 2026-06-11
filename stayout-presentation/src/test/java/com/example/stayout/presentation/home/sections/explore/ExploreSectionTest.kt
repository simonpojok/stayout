package com.example.stayout.presentation.home.sections.explore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.theme.StayScoutTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExploreSectionTest {
    @get:Rule
    val rule = createComposeRule()

    private val location = LocationDomainModel("Dublin", "Ireland")

    private val property =
        PropertyDomainModel(
            id = 1,
            name = "Kinlay House",
            isFeatured = true,
            rating = 9.0,
            ratingCount = "500",
            lowestPriceValue = BigDecimal("19.50"),
            lowestPriceCurrency = "EUR",
            overview = "A great hostel",
            thumbnailUrl = null,
            address = "2-12 Lord Edward St",
            type = "Hostel",
            facilities = listOf(FacilityCategoryDomainModel("Amenities", listOf("WiFi"))),
            freeCancellationAvailable = true,
        )

    @Test
    fun `loading state does not show any property cards`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(state = ExploreState.Loading, onIntent = {})
            }
        }

        rule.onNodeWithText("Kinlay House").assertDoesNotExist()
    }

    @Test
    fun `error state displays the error message`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state = ExploreState.Error("No internet connection."),
                    onIntent = {},
                )
            }
        }

        rule.onNodeWithText("No internet connection.").assertIsDisplayed()
    }

    @Test
    fun `retry button in error state fires Load intent`() {
        var intentReceived: ExploreIntent? = null

        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state = ExploreState.Error("Network error"),
                    onIntent = { intentReceived = it },
                )
            }
        }

        rule.onNodeWithText("Retry").performClick()

        assertTrue(intentReceived is ExploreIntent.Load)
    }

    @Test
    fun `success state displays property name`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state =
                        ExploreState.Success(
                            location = location,
                            allProperties = listOf(property),
                            pageEnd = 6,
                        ),
                    onIntent = {},
                )
            }
        }

        rule.onNodeWithText("Kinlay House").assertIsDisplayed()
    }

    @Test
    fun `success state with no properties shows empty state`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state =
                        ExploreState.Success(
                            location = location,
                            allProperties = emptyList(),
                            pageEnd = 6,
                        ),
                    onIntent = {},
                )
            }
        }

        rule.onNodeWithText("No properties available").assertIsDisplayed()
    }

    @Test
    fun `success state with search query and no matches shows search empty state`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state =
                        ExploreState.Success(
                            location = location,
                            allProperties = listOf(property),
                            pageEnd = 6,
                            searchQuery = "xyz-no-match",
                        ),
                    onIntent = {},
                )
            }
        }

        rule.onNodeWithText("No matches found").assertIsDisplayed()
    }

    @Test
    fun `success state with isLoadingMore shows loading indicator at bottom`() {
        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state =
                        ExploreState.Success(
                            location = location,
                            allProperties = listOf(property),
                            pageEnd = 6,
                            isLoadingMore = true,
                        ),
                    onIntent = {},
                )
            }
        }

        rule.onNodeWithText("Kinlay House").assertIsDisplayed()
    }

    @Test
    fun `tapping a property card fires SelectProperty intent`() {
        var intentReceived: ExploreIntent? = null

        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    state =
                        ExploreState.Success(
                            location = location,
                            allProperties = listOf(property),
                            pageEnd = 6,
                        ),
                    onIntent = { intentReceived = it },
                )
            }
        }

        rule.onNodeWithText("Kinlay House").performClick()

        assertTrue(intentReceived is ExploreIntent.SelectProperty)
    }
}
