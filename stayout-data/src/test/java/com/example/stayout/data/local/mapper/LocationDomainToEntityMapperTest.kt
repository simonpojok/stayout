package com.example.stayout.data.local.mapper

import com.example.stayout.domain.model.LocationDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationDomainToEntityMapperTest {
    private val mapper = LocationDomainToEntityMapper()

    @Test
    fun `map converts domain model fields to entity`() {
        val domain = LocationDomainModel(cityName = "Dublin", countryName = "Ireland")

        val entity = mapper.map(domain)

        assertEquals("Dublin", entity.cityName)
        assertEquals("Ireland", entity.countryName)
    }

    @Test
    fun `map preserves special characters in city and country names`() {
        val domain = LocationDomainModel(cityName = "São Paulo", countryName = "Côte d'Ivoire")

        val entity = mapper.map(domain)

        assertEquals("São Paulo", entity.cityName)
        assertEquals("Côte d'Ivoire", entity.countryName)
    }
}
