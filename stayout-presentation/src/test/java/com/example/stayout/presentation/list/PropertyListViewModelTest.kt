package com.example.stayout.presentation.list

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
class PropertyListViewModelTest {
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

    private lateinit var viewModel: PropertyListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getPropertiesUseCase() } returns Result.success(location to properties)
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
        viewModel = PropertyListViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load transitions to Success state with first page`() {
        val state = viewModel.state.value as PropertyListState.Success

        assertEquals(location, state.location)
        assertEquals(properties, state.allProperties)
        assertEquals(6, state.displayedProperties.size)
    }

    @Test
    fun `load failure transitions to Error state`() {
        coEvery { getPropertiesUseCase() } returns Result.failure(RuntimeException("Network error"))

        val failingVm = PropertyListViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
        val state = failingVm.state.value

        assertTrue(state is PropertyListState.Error)
        assertEquals("Network error", (state as PropertyListState.Error).message)
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

        viewModel.onIntent(PropertyListIntent.Refresh)

        val state = viewModel.state.value as PropertyListState.Success
        assertEquals(newProperties, state.allProperties)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `refresh failure clears isRefreshing flag`() {
        coEvery { getPropertiesUseCase() } returns Result.failure(RuntimeException("Refresh failed"))

        viewModel.onIntent(PropertyListIntent.Refresh)

        val state = viewModel.state.value as PropertyListState.Success
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `LoadMore expands the page after delay`() =
        runTest(testDispatcher) {
            val state = viewModel.state.value as PropertyListState.Success
            assertEquals(6, state.displayedProperties.size)

            viewModel.onIntent(PropertyListIntent.LoadMore)
            advanceTimeBy(601)

            val updated = viewModel.state.value as PropertyListState.Success
            assertEquals(10, updated.displayedProperties.size)
            assertFalse(updated.isLoadingMore)
        }

    @Test
    fun `LoadMore sets isLoadingMore true before delay completes`() =
        runTest(UnconfinedTestDispatcher()) {
            val freshDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(freshDispatcher)

            val vm = PropertyListViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
            val before = vm.state.value as PropertyListState.Success
            assertTrue(before.canLoadMore)

            vm.onIntent(PropertyListIntent.LoadMore)

            val loading = vm.state.value as PropertyListState.Success
            assertTrue(loading.isLoadingMore)
        }

    @Test
    fun `SelectProperty emits NavigateToDetail event`() =
        runTest(testDispatcher) {
            val property = properties[0]

            viewModel.onIntent(PropertyListIntent.SelectProperty(property))

            val event = viewModel.events.first()
            assertTrue(event is PropertyListEvent.NavigateToDetail)
            assertEquals(property.id, (event as PropertyListEvent.NavigateToDetail).propertyId)
        }

    @Test
    fun `UpdateSearch filters displayed properties`() {
        viewModel.onIntent(PropertyListIntent.UpdateSearch("Property 3"))

        val state = viewModel.state.value as PropertyListState.Success
        assertEquals(1, state.displayedProperties.size)
        assertEquals("Property 3", state.displayedProperties[0].name)
    }

    @Test
    fun `UpdateSearch with blank query restores paged results`() {
        viewModel.onIntent(PropertyListIntent.UpdateSearch("Property 1"))
        viewModel.onIntent(PropertyListIntent.UpdateSearch(""))

        val state = viewModel.state.value as PropertyListState.Success
        assertEquals(6, state.displayedProperties.size)
    }

    @Test
    fun `ToggleTheme emits ToggleTheme event`() =
        runTest(testDispatcher) {
            viewModel.onIntent(PropertyListIntent.ToggleTheme)

            val event = viewModel.events.first()
            assertTrue(event is PropertyListEvent.ToggleTheme)
        }

    @Test
    fun `network offline status updates isOffline in Success state`() {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(false)

        val offlineVm = PropertyListViewModel(getPropertiesUseCase, observeNetworkStatusUseCase, savedStateHandle)
        val state = offlineVm.state.value as PropertyListState.Success
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
        viewModel.onIntent(PropertyListIntent.UpdateSearch("Property 1"))
        val stateBeforeLoad = viewModel.state.value as PropertyListState.Success
        assertFalse(stateBeforeLoad.canLoadMore)

        viewModel.onIntent(PropertyListIntent.LoadMore)

        val stateAfter = viewModel.state.value as PropertyListState.Success
        assertFalse(stateAfter.isLoadingMore)
    }
}
