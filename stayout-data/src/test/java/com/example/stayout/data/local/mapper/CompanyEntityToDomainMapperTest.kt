package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.CompanyEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyEntityToDomainMapperTest {
    private val mapper = CompanyEntityToDomainMapper()

    @Test
    fun `map converts entity to domain model`() {
        val entity =
            CompanyEntity(
                name = "Romaguera-Crona",
                catchPhrase = "Multi-layered client-server neural-net",
                bs = "harness real-time e-markets",
            )

        val domain = mapper.map(entity)

        assertEquals("Romaguera-Crona", domain.name)
        assertEquals("Multi-layered client-server neural-net", domain.catchPhrase)
        assertEquals("harness real-time e-markets", domain.bs)
    }

    @Test
    fun `map preserves empty strings`() {
        val entity = CompanyEntity(name = "", catchPhrase = "", bs = "")

        val domain = mapper.map(entity)

        assertEquals("", domain.name)
        assertEquals("", domain.catchPhrase)
        assertEquals("", domain.bs)
    }
}
