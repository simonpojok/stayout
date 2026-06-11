package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AddressDomainModelTest {
    private val model =
        AddressDomainModel(
            street = "Kulas Light",
            suite = "Apt 556",
            city = "Gwenborough",
            zipcode = "92998-3874",
            latitude = -37.3159,
            longitude = 81.1496,
        )

    @Test
    fun `holds all address fields`() {
        assertEquals("Kulas Light", model.street)
        assertEquals("Apt 556", model.suite)
        assertEquals("Gwenborough", model.city)
        assertEquals("92998-3874", model.zipcode)
        assertEquals(-37.3159, model.latitude, 0.0001)
        assertEquals(81.1496, model.longitude, 0.0001)
    }

    @Test
    fun `copy produces updated model`() {
        val copy = model.copy(city = "Shelbyville")
        assertEquals("Shelbyville", copy.city)
        assertEquals(model.street, copy.street)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }
}
