package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyDomainModelTest {
    private val model =
        CompanyDomainModel(
            name = "Romaguera-Crona",
            catchPhrase = "Multi-layered client-server neural-net",
            bs = "harness real-time e-markets",
        )

    @Test
    fun `holds all company fields`() {
        assertEquals("Romaguera-Crona", model.name)
        assertEquals("Multi-layered client-server neural-net", model.catchPhrase)
        assertEquals("harness real-time e-markets", model.bs)
    }

    @Test
    fun `copy produces updated model`() {
        val copy = model.copy(name = "Globex")
        assertEquals("Globex", copy.name)
        assertEquals(model.catchPhrase, copy.catchPhrase)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }
}
