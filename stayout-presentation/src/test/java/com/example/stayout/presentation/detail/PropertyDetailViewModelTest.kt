package com.example.stayout.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.usecase.GetExchangeRatesUseCase
import com.example.stayout.domain.usecase.GetPropertyByIdUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
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
            facilities = listOf(FacilityCategoryDomainModel("Amenities", listOf("WiFi"))),
            freeCancellationAvailable = true,
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
        viewModel =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load transitions to Success state with property and rates`() {
        val state = viewModel.state.value as PropertyDetailState.Success

        assertEquals(property, state.property)
        assertEquals(rates, state.rates)
        assertFalse(state.ratesUnavailable)
        assertEquals(CurrencyDomainModel.EUR, state.selectedCurrency)
    }

    @Test
    fun `load shows Error when property is not found`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null

        val vm =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
        val state = vm.state.value

        assertTrue(state is PropertyDetailState.Error)
        assertEquals("Property not found", (state as PropertyDetailState.Error).message)
    }

    @Test
    fun `load succeeds with fallback rates when rates fetch fails`() {
        coEvery { getExchangeRatesUseCase() } returns Result.failure(RuntimeException("No rates"))

        val vm =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
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
    fun `Retry reloads data after an error`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null
        val vm =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
        assertTrue(vm.state.value is PropertyDetailState.Error)

        coEvery { getPropertyByIdUseCase(propertyId) } returns property
        vm.onIntent(PropertyDetailIntent.Retry)

        assertTrue(vm.state.value is PropertyDetailState.Success)
    }

    @Test
    fun `network offline status reflects in Success state`() {
        coEvery { observeNetworkStatusUseCase() } returns flowOf(false)

        val vm =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
        val state = vm.state.value as PropertyDetailState.Success
        assertTrue(state.isOffline)
    }

    @Test
    fun `SelectCurrency is ignored when state is not Success`() {
        coEvery { getPropertyByIdUseCase(propertyId) } returns null
        val vm =
            PropertyDetailViewModel(
                getPropertyByIdUseCase,
                getExchangeRatesUseCase,
                observeNetworkStatusUseCase,
                savedStateHandle,
            )
        assertTrue(vm.state.value is PropertyDetailState.Error)

        vm.onIntent(PropertyDetailIntent.SelectCurrency(CurrencyDomainModel.USD))

        assertTrue(vm.state.value is PropertyDetailState.Error)
    }
}
