package com.example.stayout.data.repository

import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.entity.ExchangeRatesEntity
import com.example.stayout.data.local.mapper.ExchangeRatesDomainToEntityMapper
import com.example.stayout.data.local.mapper.ExchangeRatesEntityToDomainMapper
import com.example.stayout.data.mapper.ExchangeRatesToDomainMapper
import com.example.stayout.data.remote.api.RatesApi
import com.example.stayout.data.remote.model.RatesResponseDataModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class RatesRepositoryImplTest {
    private val api = mockk<RatesApi>()
    private val mapper = mockk<ExchangeRatesToDomainMapper>()
    private val statsRepository = mockk<StatsRepository>(relaxed = true)
    private val ratesDao = mockk<ExchangeRatesDao>(relaxed = true)
    private val ratesEntityToDomain = mockk<ExchangeRatesEntityToDomainMapper>()
    private val ratesDomainToEntity = mockk<ExchangeRatesDomainToEntityMapper>()

    private val repository =
        RatesRepositoryImpl(
            api = api,
            mapper = mapper,
            statsRepository = statsRepository,
            ratesDao = ratesDao,
            ratesEntityToDomain = ratesEntityToDomain,
            ratesDomainToEntity = ratesDomainToEntity,
        )

    private val stubResponse =
        RatesResponseDataModel(
            success = true,
            base = "EUR",
            date = LocalDate.of(2024, 1, 1),
            rates = mapOf("USD" to BigDecimal("1.08"), "GBP" to BigDecimal("0.86")),
        )
    private val stubRates =
        ExchangeRatesDomainModel(
            usd = BigDecimal("1.08"),
            gbp = BigDecimal("0.86"),
        )
    private val stubRatesEntity = ExchangeRatesEntity(usd = "1.08", gbp = "0.86")

    init {
        every { ratesDomainToEntity.map(stubRates) } returns stubRatesEntity
        coEvery { ratesDao.get() } returns null
    }

    @Test
    fun `returns success with mapped rates on successful API call`() =
        runTest {
            coEvery { api.getRates() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubRates

            val result = repository.getExchangeRates()

            assertTrue(result.isSuccess)
            assertEquals(stubRates, result.getOrNull())
        }

    @Test
    fun `tracks LOAD_RATES stats event on success`() =
        runTest {
            coEvery { api.getRates() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubRates

            repository.getExchangeRates()

            verify { statsRepository.trackEvent(StatsEvent.LOAD_RATES, any()) }
        }

    @Test
    fun `returns failure when API throws and no cache exists`() =
        runTest {
            val exception = RuntimeException("Network error")
            coEvery { api.getRates() } throws exception

            val result = repository.getExchangeRates()

            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }

    @Test
    fun `does not track stats event when API throws`() =
        runTest {
            coEvery { api.getRates() } throws RuntimeException("error")

            repository.getExchangeRates()

            verify(exactly = 0) { statsRepository.trackEvent(any(), any()) }
        }

    @Test
    fun `returns cached rates when API throws after a successful call`() =
        runTest {
            coEvery { api.getRates() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubRates
            repository.getExchangeRates()

            coEvery { api.getRates() } throws RuntimeException("Network error")
            coEvery { ratesDao.get() } returns stubRatesEntity
            every { ratesEntityToDomain.map(stubRatesEntity) } returns stubRates

            val result = repository.getExchangeRates()

            assertTrue(result.isSuccess)
            assertEquals(stubRates, result.getOrNull())
        }
}
