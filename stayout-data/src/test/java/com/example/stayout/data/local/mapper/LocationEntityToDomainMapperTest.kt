package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.LocationEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationEntityToDomainMapperTest {
    private val mapper = LocationEntityToDomainMapper()

    @Test
    fun `map converts entity fields to domain model`() {
        val entity = LocationEntity(cityName = "Dublin", countryName = "Ireland")

        val domain = mapper.map(entity)

        assertEquals("Dublin", domain.cityName)
        assertEquals("Ireland", domain.countryName)
    }

    @Test
    fun `map preserves special characters in city and country names`() {
        val entity = LocationEntity(cityName = "Zürich", countryName = "Switzerland")

        val domain = mapper.map(entity)

        assertEquals("Zürich", domain.cityName)
        assertEquals("Switzerland", domain.countryName)
    }
}
