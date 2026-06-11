package com.example.stayout.data.local.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class AddressEntityTest {
    private val entity =
        AddressEntity(
            street = "123 Main St",
            suite = "Apt 4",
            city = "Springfield",
            zipcode = "12345",
            geoLat = "40.7128",
            geoLng = "-74.0060",
        )

    @Test
    fun `holds all address fields`() {
        assertEquals("123 Main St", entity.street)
        assertEquals("Apt 4", entity.suite)
        assertEquals("Springfield", entity.city)
        assertEquals("12345", entity.zipcode)
        assertEquals("40.7128", entity.geoLat)
        assertEquals("-74.0060", entity.geoLng)
    }

    @Test
    fun `copy produces updated entity`() {
        val copy = entity.copy(city = "Shelbyville")
        assertEquals("Shelbyville", copy.city)
        assertEquals(entity.street, copy.street)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = entity.copy()
        assertEquals(entity, other)
        assertEquals(entity.hashCode(), other.hashCode())
    }
}
