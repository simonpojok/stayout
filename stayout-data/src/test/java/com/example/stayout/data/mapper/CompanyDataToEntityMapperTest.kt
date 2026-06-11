package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CompanyDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyDataToEntityMapperTest {
    private val mapper = CompanyDataToEntityMapper()

    @Test
    fun `map converts data model to entity`() {
        val model =
            CompanyDataModel(
                name = "Romaguera-Crona",
                catchPhrase = "Multi-layered client-server neural-net",
                bs = "harness real-time e-markets",
            )

        val entity = mapper.map(model)

        assertEquals("Romaguera-Crona", entity.name)
        assertEquals("Multi-layered client-server neural-net", entity.catchPhrase)
        assertEquals("harness real-time e-markets", entity.bs)
    }

    @Test
    fun `map preserves empty strings`() {
        val model = CompanyDataModel(name = "", catchPhrase = "", bs = "")

        val entity = mapper.map(model)

        assertEquals("", entity.name)
        assertEquals("", entity.catchPhrase)
        assertEquals("", entity.bs)
    }
}
