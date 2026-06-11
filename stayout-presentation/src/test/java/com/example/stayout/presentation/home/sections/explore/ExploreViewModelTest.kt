package com.example.stayout.presentation.home.sections.explore

import androidx.lifecycle.SavedStateHandle
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private val getPropertiesUseCase: GetPropertiesUseCase = mockk()
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase = mockk()
    private val savedStateHandle = SavedStateHandle()

    private val location = LocationDomainModel("Dublin", "Ireland")
    private val properties =
        (1..10).map { id ->
            PropertyDomainModel(
                id = id,
                name = "Property $id",
                isFeatured = false,
                rating = 8.0,
                ratingCount = "100",
                lowestPriceValue = BigDecimal("15.00"),
                lowestPriceCurrency = "EUR",
                overview = "Overview $id",
                thumbnailUrl = null,
                address = "Address $id",
                type = "Hostel",
                facilities = listOf(FacilityCategoryDomainModel("Amenities", listOf("WiFi"))),
                freeCancellationAvailable = false,
            )
        }

    private lateinit var viewModel: ExploreViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getPropertiesUseCase() } returns Result.success(location to properties)
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
        viewModel = ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load transitions to Success state with first page`() {
        val state = viewModel.state.value as ExploreState.Success

        assertEquals(location, state.location)
        assertEquals(properties, state.allProperties)
        assertEquals(6, state.displayedProperties.size)
    }

    @Test
    fun `load failure transitions to Error state`() {
        coEvery { getPropertiesUseCase() } returns Result.failure(RuntimeException("Network error"))

        val failingVm = ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
        val state = failingVm.state.value

        assertTrue(state is ExploreState.Error)
        assertEquals("Network error", (state as ExploreState.Error).message)
    }

    @Test
    fun `refresh reloads properties and resets to first page`() {
        val newProperties =
            listOf(
                PropertyDomainModel(
                    id = 99,
                    name = "New Property",
                    isFeatured = true,
                    rating = 9.0,
                    ratingCount = "500",
                    lowestPriceValue = BigDecimal("25.00"),
                    lowestPriceCurrency = "EUR",
                    overview = "Fresh data",
                    thumbnailUrl = null,
                    address = "New Address",
                    type = "Hotel",
                    facilities = emptyList(),
                    freeCancellationAvailable = true,
                ),
            )
        coEvery { getPropertiesUseCase() } returns Result.success(location to newProperties)

        viewModel.onIntent(ExploreIntent.Refresh)

        val state = viewModel.state.value as ExploreState.Success
        assertEquals(newProperties, state.allProperties)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `refresh failure clears isRefreshing flag`() {
        coEvery { getPropertiesUseCase() } returns Result.failure(RuntimeException("Refresh failed"))

        viewModel.onIntent(ExploreIntent.Refresh)

        val state = viewModel.state.value as ExploreState.Success
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `LoadMore expands the page after delay`() =
        runTest(testDispatcher) {
            val state = viewModel.state.value as ExploreState.Success
            assertEquals(6, state.displayedProperties.size)

            viewModel.onIntent(ExploreIntent.LoadMore)
            advanceTimeBy(601)

            val updated = viewModel.state.value as ExploreState.Success
            assertEquals(10, updated.displayedProperties.size)
            assertFalse(updated.isLoadingMore)
        }

    @Test
    fun `LoadMore sets isLoadingMore true before delay completes`() =
        runTest(UnconfinedTestDispatcher()) {
            val freshDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(freshDispatcher)

            val vm = ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
            val before = vm.state.value as ExploreState.Success
            assertTrue(before.canLoadMore)

            vm.onIntent(ExploreIntent.LoadMore)

            val loading = vm.state.value as ExploreState.Success
            assertTrue(loading.isLoadingMore)
        }

    @Test
    fun `SelectProperty emits NavigateToDetail event`() =
        runTest(testDispatcher) {
            val property = properties[0]

            viewModel.onIntent(ExploreIntent.SelectProperty(property))

            val event = viewModel.events.first()
            assertTrue(event is ExploreEvent.NavigateToDetail)
            assertEquals(property.id, (event as ExploreEvent.NavigateToDetail).propertyId)
        }

    @Test
    fun `UpdateSearch filters displayed properties`() {
        viewModel.onIntent(ExploreIntent.UpdateSearch("Property 3"))

        val state = viewModel.state.value as ExploreState.Success
        assertEquals(1, state.displayedProperties.size)
        assertEquals("Property 3", state.displayedProperties[0].name)
    }

    @Test
    fun `UpdateSearch with blank query restores paged results`() {
        viewModel.onIntent(ExploreIntent.UpdateSearch("Property 1"))
        viewModel.onIntent(ExploreIntent.UpdateSearch(""))

        val state = viewModel.state.value as ExploreState.Success
        assertEquals(6, state.displayedProperties.size)
    }

    @Test
    fun `network offline status updates isOffline in Success state`() {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(false)

        val offlineVm = ExploreViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
        val state = offlineVm.state.value as ExploreState.Success
        assertTrue(state.isOffline)
    }

    @Test
    fun `saveScrollPosition persists index to SavedStateHandle`() {
        viewModel.saveScrollPosition(5)
        assertEquals(5, viewModel.scrollIndex)
    }

    @Test
    fun `scrollIndex defaults to zero when SavedStateHandle is empty`() {
        assertEquals(0, viewModel.scrollIndex)
    }

    @Test
    fun `LoadMore intent is ignored when canLoadMore is false`() {
        viewModel.onIntent(ExploreIntent.UpdateSearch("Property 1"))
        val stateBeforeLoad = viewModel.state.value as ExploreState.Success
        assertFalse(stateBeforeLoad.canLoadMore)

        viewModel.onIntent(ExploreIntent.LoadMore)

        val stateAfter = viewModel.state.value as ExploreState.Success
        assertFalse(stateAfter.isLoadingMore)
    }
}
