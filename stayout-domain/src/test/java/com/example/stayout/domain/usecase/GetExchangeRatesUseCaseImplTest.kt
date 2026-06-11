package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.repository.RatesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class GetExchangeRatesUseCaseImplTest {
    private val repository = mockk<RatesRepository>()

    private val useCase =
        GetExchangeRatesUseCaseImpl(
            repository = repository,
            dispatcher = Dispatchers.Unconfined,
        )

    private val stubRates =
        ExchangeRatesDomainModel(
            eur = BigDecimal.ONE,
            usd = BigDecimal("1.08"),
            gbp = BigDecimal("0.86"),
        )

    @Test
    fun `returns exchange rates on success`() =
        runTest {
            coEvery { repository.getExchangeRates() } returns Result.success(stubRates)

            val result = useCase()

            assertTrue(result.isSuccess)
            assertEquals(stubRates, result.getOrNull())
        }

    @Test
    fun `propagates failure from repository`() =
        runTest {
            val exception = RuntimeException("Rates unavailable")
            coEvery { repository.getExchangeRates() } returns Result.failure(exception)

            val result = useCase()

            assertTrue(result.isFailure)
            assertEquals("Rates unavailable", result.exceptionOrNull()?.message)
        }
}
