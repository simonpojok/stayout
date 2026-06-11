package com.example.stayout.data.local.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyEntityTest {
    private val entity =
        CompanyEntity(
            name = "Acme Corp",
            catchPhrase = "Multi-layered client-server neural-net",
            bs = "harness real-time e-markets",
        )

    @Test
    fun `holds all company fields`() {
        assertEquals("Acme Corp", entity.name)
        assertEquals("Multi-layered client-server neural-net", entity.catchPhrase)
        assertEquals("harness real-time e-markets", entity.bs)
    }

    @Test
    fun `copy produces updated entity`() {
        val copy = entity.copy(name = "Globex")
        assertEquals("Globex", copy.name)
        assertEquals(entity.catchPhrase, copy.catchPhrase)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = entity.copy()
        assertEquals(entity, other)
        assertEquals(entity.hashCode(), other.hashCode())
    }
}
