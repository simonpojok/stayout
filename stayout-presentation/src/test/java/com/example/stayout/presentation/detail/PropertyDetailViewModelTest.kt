package com.example.stayout.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.usecase.GetExchangeRatesUseCase
import com.example.stayout.domain.usecase.GetPropertyByIdUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.TrackEventUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class PropertyDetailViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private val getPropertyByIdUseCase: GetPropertyByIdUseCase = mockk()
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase = mockk()
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase = mockk()
    private val trackEventUseCase: TrackEventUseCase = mockk(relaxed = true)

    private val propertyId = 42
    private val savedStateHandle =
        SavedStateHandle(
            mapOf(PropertyDetailViewModel.ARG_PROPERTY_ID to propertyId),
        )

    private val property =
        PropertyDomainModel(
            id = propertyId,
            name = "Kinlay House",
            isFeatured = true,
            rating = 9.0,
            ratingCount = "500",
            lowestPriceValue = BigDecimal("19.50"),
            lowestPriceCurrency = "EUR",
            overview = "A great hostel",
            thumbnailUrl = "https://example.com/thumb.jpg",
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
            latitude = 53.3437259,
            longitude = -6.269898,
        )

    private val rates =
        ExchangeRatesDomainModel(
            usd = BigDecimal("1.09"),
            gbp = BigDecimal("0.86"),
        )

    private lateinit var viewModel: PropertyDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getPropertyByIdUseCase(propertyId) } returns property
        coEvery { getExchangeRatesUseCase() } returns Result.success(rates)
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
        viewModel = createViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() =
        PropertyDetailViewModel(
            getPropertyByIdUseCase,
            getExchangeRatesUseCase,
            observeNetworkStatusUseCase,
            trackEventUseCase,
            savedStateHandle,
        )

    @Test
    fun `initial load transitions to Success state with property and rates`() {
        val state = viewModel.state.value as PropertyDetailState.Success

        assertEquals(property, state.property)
        assertEquals(rates, state.rates)
        assertFalse(state.ratesUnavailable)
        assertEquals(CurrencyDomainModel.EUR, state.selectedCurrency)
        assertEquals(property.latitude, state.property.latitude, 0.0)
        assertEquals(property.longitude, state.property.longitude, 0.0)
    }

    @Test
    fun `load shows Error when property is not found`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null

        val vm = createViewModel()
        val state = vm.state.value

        assertTrue(state is PropertyDetailState.Error)
        assertEquals("Property not found", (state as PropertyDetailState.Error).message)
    }

    @Test
    fun `load succeeds with fallback rates when rates fetch fails`() {
        coEvery { getExchangeRatesUseCase() } returns Result.failure(RuntimeException("No rates"))

        val vm = createViewModel()
        val state = vm.state.value as PropertyDetailState.Success

        assertEquals(property, state.property)
        assertTrue(state.ratesUnavailable)
        assertEquals(BigDecimal.ONE, state.rates.usd)
        assertEquals(BigDecimal.ONE, state.rates.gbp)
    }

    @Test
    fun `SelectCurrency updates selectedCurrency in Success state`() {
        viewModel.onIntent(PropertyDetailIntent.SelectCurrency(CurrencyDomainModel.USD))

        val state = viewModel.state.value as PropertyDetailState.Success
        assertEquals(CurrencyDomainModel.USD, state.selectedCurrency)
    }

    @Test
    fun `SelectCurrency to GBP updates selectedCurrency`() {
        viewModel.onIntent(PropertyDetailIntent.SelectCurrency(CurrencyDomainModel.GBP))

        val state = viewModel.state.value as PropertyDetailState.Success
        assertEquals(CurrencyDomainModel.GBP, state.selectedCurrency)
    }

    @Test
    fun `SelectCurrency emits CurrencyChanged analytics event`() =
        runTest(testDispatcher) {
            viewModel.onIntent(PropertyDetailIntent.SelectCurrency(CurrencyDomainModel.USD))

            io.mockk.coVerify {
                trackEventUseCase(AnalyticsEvent.CurrencyChanged("EUR", "USD"))
            }
        }

    @Test
    fun `Retry reloads data after an error`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null
        val vm = createViewModel()
        assertTrue(vm.state.value is PropertyDetailState.Error)

        coEvery { getPropertyByIdUseCase(propertyId) } returns property
        vm.onIntent(PropertyDetailIntent.Retry)

        assertTrue(vm.state.value is PropertyDetailState.Success)
    }

    @Test
    fun `network offline status reflects in Success state`() {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(false)

        val vm = createViewModel()
        val state = vm.state.value as PropertyDetailState.Success
        assertTrue(state.isOffline)
    }

    @Test
    fun `Book intent emits ShowComingSoon event`() =
        runTest(testDispatcher) {
            viewModel.onIntent(PropertyDetailIntent.Book)

            val event = viewModel.events.first()
            assertTrue(event is PropertyDetailEvent.ShowComingSoon)
        }

    @Test
    fun `Book intent does not change state`() {
        val before = viewModel.state.value

        viewModel.onIntent(PropertyDetailIntent.Book)

        assertEquals(before, viewModel.state.value)
    }

    @Test
    fun `Share intent does not change state`() {
        val before = viewModel.state.value

        viewModel.onIntent(PropertyDetailIntent.Share)

        assertEquals(before, viewModel.state.value)
    }

    @Test
    fun `Favorite intent does not change state`() {
        val before = viewModel.state.value

        viewModel.onIntent(PropertyDetailIntent.Favorite)

        assertEquals(before, viewModel.state.value)
    }

    @Test
    fun `Location intent does not change state`() {
        val before = viewModel.state.value

        viewModel.onIntent(PropertyDetailIntent.Location)

        assertEquals(before, viewModel.state.value)
    }

    @Test
    fun `SelectCurrency is ignored when state is not Success`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null
        val vm = createViewModel()
        assertTrue(vm.state.value is PropertyDetailState.Error)

        vm.onIntent(PropertyDetailIntent.SelectCurrency(CurrencyDomainModel.USD))

        assertTrue(vm.state.value is PropertyDetailState.Error)
    }
}
