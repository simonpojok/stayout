package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CompanyDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyToDomainMapperTest {
    private val mapper = CompanyToDomainMapper()

    @Test
    fun `map converts data model to domain model`() {
        val model =
            CompanyDataModel(
                name = "Romaguera-Crona",
                catchPhrase = "Multi-layered client-server neural-net",
                bs = "harness real-time e-markets",
            )

        val domain = mapper.map(model)

        assertEquals("Romaguera-Crona", domain.name)
        assertEquals("Multi-layered client-server neural-net", domain.catchPhrase)
        assertEquals("harness real-time e-markets", domain.bs)
    }

    @Test
    fun `map preserves empty strings`() {
        val model = CompanyDataModel(name = "", catchPhrase = "", bs = "")

        val domain = mapper.map(model)

        assertEquals("", domain.name)
        assertEquals("", domain.catchPhrase)
        assertEquals("", domain.bs)
    }
}
