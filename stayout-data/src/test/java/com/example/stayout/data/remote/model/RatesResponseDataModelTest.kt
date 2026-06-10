package com.example.stayout.data.remote.model

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class RatesResponseDataModelTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `decodes numeric rate values from the live API shape`() {
        val raw =
            """
            {
              "success": true,
              "timestamp": 1710499564,
              "historical": true,
              "base": "EUR",
              "date": "2024-03-15",
              "rates": {
                "USD": 1.088993,
                "GBP": 0.853825,
                "BTC": 1.6125798e-5
              }
            }
            """.trimIndent()

        val result = json.decodeFromString<RatesResponseDataModel>(raw)

        assertEquals(true, result.success)
        assertEquals("EUR", result.base)
        assertEquals(LocalDate.of(2024, 3, 15), result.date)
        assertEquals(BigDecimal("1.088993"), result.rates.getValue("USD"))
        assertEquals(BigDecimal("0.853825"), result.rates.getValue("GBP"))
        assertEquals(BigDecimal("1.6125798e-5"), result.rates.getValue("BTC"))
    }
}
