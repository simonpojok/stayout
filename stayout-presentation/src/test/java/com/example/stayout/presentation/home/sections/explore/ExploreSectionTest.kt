package com.example.stayout.presentation.home.sections.explore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.InternetConnectionError
import com.example.stayout.domain.model.InternetConnectionException
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.TrackEventUseCase
import com.example.stayout.presentation.theme.StayScoutTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExploreSectionTest {
    @get:Rule
    val rule = createComposeRule()

    private val testDispatcher = UnconfinedTestDispatcher()
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
            facilities =
                listOf(
                    FacilityCategoryDomainModel(
                        name = "Amenities",
                        facilities = listOf(FacilityDomainModel(id = "WIFI", name = "WiFi")),
                    ),
                ),
            freeCancellationAvailable = true,
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModelWith(result: Result<Pair<LocationDomainModel, List<PropertyDomainModel>>>): ExploreViewModel {
        coEvery { getPropertiesUseCase() } returns result
        return ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle, trackEventUseCase)
    }

    @Test
    fun `loading state does not show any property cards`() {
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
        val vm =
            viewModelWith(
                Result.failure(InternetConnectionException(InternetConnectionError.NoConnection)),
            )

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("No internet connection. Check your network and try again.").assertIsDisplayed()
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
    fun `success state with search query and no matches shows search empty state`() {
        val vm = viewModelWith(Result.success(location to listOf(property)))

        rule.setContent {
            StayScoutTheme {
                ExploreSection(searchQuery = "xyz-no-match", onNavigateToDetail = {}, viewModel = vm)
            }
        }

        rule.onNodeWithText("No matches found").assertIsDisplayed()
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
