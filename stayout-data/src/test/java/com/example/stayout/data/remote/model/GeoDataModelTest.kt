package com.example.stayout.data.remote.model

import org.junit.Assert.assertEquals
import org.junit.Test

class GeoDataModelTest {
    private val model = GeoDataModel(lat = "-37.3159", lng = "81.1496")

    @Test
    fun `holds lat and lng`() {
        assertEquals("-37.3159", model.lat)
        assertEquals("81.1496", model.lng)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }

    @Test
    fun `copy produces updated model`() {
        val copy = model.copy(lat = "0.0")
        assertEquals("0.0", copy.lat)
        assertEquals(model.lng, copy.lng)
    }
}
