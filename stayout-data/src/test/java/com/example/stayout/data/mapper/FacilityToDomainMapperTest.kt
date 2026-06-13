package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class FacilityToDomainMapperTest {
    private val mapper = FacilityToDomainMapper()

    @Test
    fun `maps id and name from data model`() {
        val result = mapper.map(FacilityDataModel(id = "wifi", name = "Free WiFi"))
        assertEquals("wifi", result.id)
        assertEquals("Free WiFi", result.name)
    }

    @Test
    fun `maps empty strings without error`() {
        val result = mapper.map(FacilityDataModel(id = "", name = ""))
        assertEquals("", result.id)
        assertEquals("", result.name)
    }

    @Test
    fun `id and name are independent — does not swap them`() {
        val result = mapper.map(FacilityDataModel(id = "pool", name = "Swimming Pool"))
        assertEquals("pool", result.id)
        assertEquals("Swimming Pool", result.name)
    }
}
