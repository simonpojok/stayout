package com.example.stayout.presentation.home.sections.explore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.TrackEventUseCase
import com.example.stayout.presentation.theme.StayScoutTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class ExploreSectionTest {
    @get:Rule
    val rule = createComposeRule()

    private val getPropertiesUseCase: GetPropertiesUseCase = mockk()
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase = mockk()
    private val trackEventUseCase: TrackEventUseCase = mockk(relaxed = true)
    private val savedStateHandle = SavedStateHandle()

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
            facilities = listOf(FacilityCategoryDomainModel("Amenities", listOf())),
            freeCancellationAvailable = true,
        )

    private fun viewModelWith(result: Result<Pair<LocationDomainModel, List<PropertyDomainModel>>>): ExploreViewModel {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
        coEvery { getPropertiesUseCase() } returns result
        return ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle, trackEventUseCase)
    }

    @Test
    fun `loading state does not show any property cards`() {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
        coEvery { getPropertiesUseCase() } returns Result.failure(RuntimeException("error"))
        val vm =
            ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle, trackEventUseCase)

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("Kinlay House").assertDoesNotExist()
    }

    @Test
    fun `error state displays the error message`() {
        val vm = viewModelWith(Result.failure(RuntimeException("No internet connection.")))

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("No internet connection.").assertIsDisplayed()
    }

    @Test
    fun `success state displays property name`() {
        val vm = viewModelWith(Result.success(location to listOf(property)))

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("Kinlay House").assertIsDisplayed()
    }

    @Test
    fun `success state with no properties shows empty state`() {
        val vm = viewModelWith(Result.success(location to emptyList()))

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("No properties available").assertIsDisplayed()
    }

    @Test
    fun `tapping a property card calls onNavigateToDetail`() {
        val vm = viewModelWith(Result.success(location to listOf(property)))
        var navigatedId: Int? = null

        rule.setContent {
            StayScoutTheme {
                ExploreSection(
                    searchQuery = "",
                    onNavigateToDetail = { navigatedId = it },
                    viewModel = vm,
                )
            }
        }

        rule.onNodeWithText("Kinlay House").performClick()

        assert(navigatedId == property.id)
    }
}
