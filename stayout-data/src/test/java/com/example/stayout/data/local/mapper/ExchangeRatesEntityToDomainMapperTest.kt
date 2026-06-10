package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.ExchangeRatesEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ExchangeRatesEntityToDomainMapperTest {
    private val mapper = ExchangeRatesEntityToDomainMapper()

    @Test
    fun `map converts entity strings to BigDecimal domain model`() {
        val entity = ExchangeRatesEntity(usd = "1.088993", gbp = "0.857442")

        val domain = mapper.map(entity)

        assertEquals(BigDecimal("1.088993"), domain.usd)
        assertEquals(BigDecimal("0.857442"), domain.gbp)
    }

    @Test
    fun `map handles integer-valued strings`() {
        val entity = ExchangeRatesEntity(usd = "1", gbp = "1")

        val domain = mapper.map(entity)

        assertEquals(BigDecimal("1"), domain.usd)
        assertEquals(BigDecimal("1"), domain.gbp)
    }

    @Test
    fun `map preserves high-precision decimal strings`() {
        val entity = ExchangeRatesEntity(usd = "1.23456789012345", gbp = "0.98765432101234")

        val domain = mapper.map(entity)

        assertEquals(BigDecimal("1.23456789012345"), domain.usd)
        assertEquals(BigDecimal("0.98765432101234"), domain.gbp)
    }
}
