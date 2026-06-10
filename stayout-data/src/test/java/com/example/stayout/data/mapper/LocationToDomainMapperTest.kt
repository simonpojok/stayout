package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CityDataModel
import com.example.stayout.data.remote.model.LocationDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationToDomainMapperTest {
    private val mapper = LocationToDomainMapper()

    @Test
    fun `maps city name correctly`() {
        val model = locationDataModel(cityName = "Dublin")
        assertEquals("Dublin", mapper.map(model).cityName)
    }

    @Test
    fun `maps country name correctly`() {
        val model = locationDataModel(country = "Ireland")
        assertEquals("Ireland", mapper.map(model).countryName)
    }

    @Test
    fun `maps both fields correctly`() {
        val model = locationDataModel(cityName = "London", country = "United Kingdom")
        val result = mapper.map(model)
        assertEquals("London", result.cityName)
        assertEquals("United Kingdom", result.countryName)
    }

    private fun locationDataModel(
        cityName: String = "City",
        country: String = "Country",
    ) = LocationDataModel(city = CityDataModel(id = 1, name = cityName, idCountry = 10, country = country))
}
