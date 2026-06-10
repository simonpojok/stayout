package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.LocationDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationToPresentationMapperTest {
    private val mapper = LocationToPresentationMapper()

    @Test
    fun `maps cityName correctly`() {
        val domain = LocationDomainModel(cityName = "Dublin", countryName = "Ireland")
        assertEquals("Dublin", mapper.map(domain).cityName)
    }

    @Test
    fun `maps countryName correctly`() {
        val domain = LocationDomainModel(cityName = "Dublin", countryName = "Ireland")
        assertEquals("Ireland", mapper.map(domain).countryName)
    }

    @Test
    fun `builds displayText as city comma country`() {
        val domain = LocationDomainModel(cityName = "Dublin", countryName = "Ireland")
        assertEquals("Dublin, Ireland", mapper.map(domain).displayText)
    }

    @Test
    fun `displayText uses exact city and country values`() {
        val domain = LocationDomainModel(cityName = "New York", countryName = "United States")
        assertEquals("New York, United States", mapper.map(domain).displayText)
    }
}
