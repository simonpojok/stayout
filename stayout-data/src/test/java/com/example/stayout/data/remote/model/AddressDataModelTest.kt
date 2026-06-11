package com.example.stayout.data.remote.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AddressDataModelTest {
    private val geo = GeoDataModel(lat = "-37.3159", lng = "81.1496")
    private val model =
        AddressDataModel(
            street = "Kulas Light",
            suite = "Apt 556",
            city = "Gwenborough",
            zipcode = "92998-3874",
            geo = geo,
        )

    @Test
    fun `holds all address fields`() {
        assertEquals("Kulas Light", model.street)
        assertEquals("Apt 556", model.suite)
        assertEquals("Gwenborough", model.city)
        assertEquals("92998-3874", model.zipcode)
        assertEquals(geo, model.geo)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }
}
