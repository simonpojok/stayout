package com.example.stayout.presentation.model

import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class PropertyUiModelTest {
    private val rates =
        ExchangeRatesDomainModel(
            usd = BigDecimal("1.08"),
            gbp = BigDecimal("0.86"),
        )

    private val model =
        PropertyUiModel(
            id = 1,
            name = "Test",
            isFeatured = false,
            formattedRating = "8.5",
            ratingCount = "100",
            lowestPriceEur = BigDecimal("14.18"),
            formattedBasePrice = "€14.18",
            overview = "",
            thumbnailUrl = null,
            address = "",
            type = "Hostel",
            facilities = emptyList(),
            freeCancellationAvailable = false,
        )

    @Test
    fun `priceIn EUR returns EUR symbol with original price`() {
        assertEquals("€14.18", model.priceIn(CurrencyDomainModel.EUR, rates))
    }

    @Test
    fun `priceIn USD multiplies by usd rate`() {
        assertEquals("\$15.31", model.priceIn(CurrencyDomainModel.USD, rates))
    }

    @Test
    fun `priceIn GBP multiplies by gbp rate`() {
        assertEquals("£12.19", model.priceIn(CurrencyDomainModel.GBP, rates))
    }

    @Test
    fun `priceIn rounds HALF_UP to 2 decimal places`() {
        val m = model.copy(lowestPriceEur = BigDecimal("10.005"))
        assertEquals("€10.01", m.priceIn(CurrencyDomainModel.EUR, rates))
    }

    @Test
    fun `priceIn EUR ignores rates`() {
        val highRates = rates.copy(usd = BigDecimal("999"), gbp = BigDecimal("999"))
        assertEquals("€14.18", model.priceIn(CurrencyDomainModel.EUR, highRates))
    }

    @Test
    fun `priceIn with zero price returns zero formatted`() {
        val m = model.copy(lowestPriceEur = BigDecimal.ZERO)
        assertEquals("€0.00", m.priceIn(CurrencyDomainModel.EUR, rates))
        assertEquals("\$0.00", m.priceIn(CurrencyDomainModel.USD, rates))
        assertEquals("£0.00", m.priceIn(CurrencyDomainModel.GBP, rates))
    }
}
