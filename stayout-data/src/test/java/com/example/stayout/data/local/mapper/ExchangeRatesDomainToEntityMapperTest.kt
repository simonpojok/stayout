package com.example.stayout.data.local.mapper

import com.example.stayout.domain.model.ExchangeRatesDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ExchangeRatesDomainToEntityMapperTest {
    private val mapper = ExchangeRatesDomainToEntityMapper()

    @Test
    fun `map converts domain model to entity with plain string values`() {
        val domain =
            ExchangeRatesDomainModel(
                usd = BigDecimal("1.088993"),
                gbp = BigDecimal("0.857442"),
            )

        val entity = mapper.map(domain)

        assertEquals("1.088993", entity.usd)
        assertEquals("0.857442", entity.gbp)
    }

    @Test
    fun `map uses toPlainString so scientific notation is not produced`() {
        val domain =
            ExchangeRatesDomainModel(
                usd = BigDecimal("0.00001"),
                gbp = BigDecimal("1000000"),
            )

        val entity = mapper.map(domain)

        assertEquals("0.00001", entity.usd)
        assertEquals("1000000", entity.gbp)
    }

    @Test
    fun `map preserves exact decimal precision`() {
        val domain =
            ExchangeRatesDomainModel(
                usd = BigDecimal("1.23456789012345"),
                gbp = BigDecimal("0.98765432101234"),
            )

        val entity = mapper.map(domain)

        assertEquals("1.23456789012345", entity.usd)
        assertEquals("0.98765432101234", entity.gbp)
    }
}
