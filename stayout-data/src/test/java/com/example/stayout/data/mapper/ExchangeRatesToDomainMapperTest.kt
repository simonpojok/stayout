package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.RatesResponseDataModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class ExchangeRatesToDomainMapperTest {
    private val mapper = ExchangeRatesToDomainMapper()

    @Test
    fun `maps USD rate correctly`() {
        val result = mapper.map(buildRates(mapOf("USD" to BigDecimal("1.08"), "GBP" to BigDecimal("0.86"))))
        assertEquals(BigDecimal("1.08"), result.usd)
    }

    @Test
    fun `maps GBP rate correctly`() {
        val result = mapper.map(buildRates(mapOf("USD" to BigDecimal("1.08"), "GBP" to BigDecimal("0.86"))))
        assertEquals(BigDecimal("0.86"), result.gbp)
    }

    @Test
    fun `falls back to ONE when USD is missing`() {
        val result = mapper.map(buildRates(mapOf("GBP" to BigDecimal("0.86"))))
        assertEquals(BigDecimal.ONE, result.usd)
    }

    @Test
    fun `falls back to ONE when GBP is missing`() {
        val result = mapper.map(buildRates(mapOf("USD" to BigDecimal("1.08"))))
        assertEquals(BigDecimal.ONE, result.gbp)
    }

    @Test
    fun `falls back to ONE for both when rates map is empty`() {
        val result = mapper.map(buildRates(emptyMap()))
        assertEquals(BigDecimal.ONE, result.usd)
        assertEquals(BigDecimal.ONE, result.gbp)
    }

    @Test
    fun `ignores unrelated currency keys`() {
        val result =
            mapper.map(
                buildRates(
                    mapOf(
                        "USD" to BigDecimal("1.08"),
                        "GBP" to BigDecimal("0.86"),
                        "JPY" to BigDecimal("160"),
                    ),
                ),
            )
        assertEquals(BigDecimal("1.08"), result.usd)
        assertEquals(BigDecimal("0.86"), result.gbp)
    }

    private fun buildRates(rates: Map<String, BigDecimal>) =
        RatesResponseDataModel(
            success = true,
            base = "EUR",
            date = LocalDate.of(2024, 1, 1),
            rates = rates,
        )
}
