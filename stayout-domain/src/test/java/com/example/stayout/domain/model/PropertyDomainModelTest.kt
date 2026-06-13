package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class PropertyDomainModelTest {
    private val rates =
        ExchangeRatesDomainModel(
            usd = BigDecimal("1.08"),
            gbp = BigDecimal("0.86"),
        )

    private val property =
        PropertyDomainModel(
            id = 1,
            name = "Test Hostel",
            isFeatured = false,
            rating = 8.5,
            ratingCount = "100",
            lowestPriceValue = BigDecimal("14.18"),
            lowestPriceCurrency = "EUR",
            overview = "Overview",
            thumbnailUrl = null,
            address = "123 Street",
            type = "Hostel",
            facilities = emptyList(),
            freeCancellationAvailable = false,
        )

    @Test
    fun `priceIn EUR returns original value with EUR symbol`() {
        val result = property.priceIn(CurrencyDomainModel.EUR, rates)
        assertEquals("€14.18", result)
    }

    @Test
    fun `priceIn USD multiplies by usd rate`() {
        val result = property.priceIn(CurrencyDomainModel.USD, rates)
        assertEquals("\$15.31", result)
    }

    @Test
    fun `priceIn GBP multiplies by gbp rate`() {
        val result = property.priceIn(CurrencyDomainModel.GBP, rates)
        assertEquals("£12.19", result)
    }

    @Test
    fun `priceIn rounds HALF_UP to 2 decimal places`() {
        val p = property.copy(lowestPriceValue = BigDecimal("10.005"))
        val result = p.priceIn(CurrencyDomainModel.EUR, rates)
        assertEquals("€10.01", result)
    }

    @Test
    fun `priceIn with zero price returns zero formatted`() {
        val p = property.copy(lowestPriceValue = BigDecimal.ZERO)
        assertEquals("€0.00", p.priceIn(CurrencyDomainModel.EUR, rates))
        assertEquals("\$0.00", p.priceIn(CurrencyDomainModel.USD, rates))
        assertEquals("£0.00", p.priceIn(CurrencyDomainModel.GBP, rates))
    }

    @Test
    fun `priceIn EUR ignores exchange rates`() {
        val highRates = rates.copy(usd = BigDecimal("999"), gbp = BigDecimal("999"))
        val result = property.priceIn(CurrencyDomainModel.EUR, highRates)
        assertEquals("€14.18", result)
    }

    @Test
    fun `priceIn USD with rate ONE returns same as EUR`() {
        val ratesAtParity = rates.copy(usd = BigDecimal.ONE)
        val eur = property.priceIn(CurrencyDomainModel.EUR, ratesAtParity)
        val usd = property.priceIn(CurrencyDomainModel.USD, ratesAtParity)
        assertEquals(eur.drop(1), usd.drop(1))
    }

    @Test
    fun `CurrencyDomainModel EUR has correct symbol and displayName`() {
        assertEquals("€", CurrencyDomainModel.EUR.symbol)
        assertEquals("EUR", CurrencyDomainModel.EUR.displayName)
    }

    @Test
    fun `CurrencyDomainModel USD has correct symbol and displayName`() {
        assertEquals("$", CurrencyDomainModel.USD.symbol)
        assertEquals("USD", CurrencyDomainModel.USD.displayName)
    }

    @Test
    fun `CurrencyDomainModel GBP has correct symbol and displayName`() {
        assertEquals("£", CurrencyDomainModel.GBP.symbol)
        assertEquals("GBP", CurrencyDomainModel.GBP.displayName)
    }

    @Test
    fun `ExchangeRatesDomainModel eur defaults to ONE`() {
        val model = ExchangeRatesDomainModel(usd = BigDecimal("1.1"), gbp = BigDecimal("0.9"))
        assertEquals(BigDecimal.ONE, model.eur)
    }

    @Test
    fun `formatIn EUR returns value with EUR symbol unchanged`() {
        val result = BigDecimal("12.50").formatIn(CurrencyDomainModel.EUR, rates)
        assertEquals("€12.50", result)
    }

    @Test
    fun `formatIn USD multiplies by usd rate`() {
        val result = BigDecimal("10.00").formatIn(CurrencyDomainModel.USD, rates)
        assertEquals("\$10.80", result)
    }

    @Test
    fun `formatIn GBP multiplies by gbp rate`() {
        val result = BigDecimal("10.00").formatIn(CurrencyDomainModel.GBP, rates)
        assertEquals("£8.60", result)
    }

    @Test
    fun `formatIn rounds HALF_UP to 2 decimal places`() {
        val result = BigDecimal("10.005").formatIn(CurrencyDomainModel.EUR, rates)
        assertEquals("€10.01", result)
    }

    @Test
    fun `formatIn zero value returns zero formatted`() {
        assertEquals("€0.00", BigDecimal.ZERO.formatIn(CurrencyDomainModel.EUR, rates))
        assertEquals("\$0.00", BigDecimal.ZERO.formatIn(CurrencyDomainModel.USD, rates))
        assertEquals("£0.00", BigDecimal.ZERO.formatIn(CurrencyDomainModel.GBP, rates))
    }
}
