package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CityDataModel
import com.example.stayout.data.remote.model.LocationDataModel
import com.example.stayout.data.remote.model.OverallRatingDataModel
import com.example.stayout.data.remote.model.PriceDataModel
import com.example.stayout.data.remote.model.PropertiesResponseDataModel
import com.example.stayout.data.remote.model.PropertyDataModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class PropertiesResponseToDomainMapperTest {
    private val locationMapper = mockk<LocationToDomainMapper>()
    private val propertyMapper = mockk<PropertyToDomainMapper>()
    private val mapper = PropertiesResponseToDomainMapper(locationMapper, propertyMapper)

    private val stubLocation = LocationDomainModel(cityName = "Dublin", countryName = "Ireland")
    private val stubProperty1 = buildDomainProperty(id = 1, name = "Hostel A")
    private val stubProperty2 = buildDomainProperty(id = 2, name = "Hostel B")

    @Test
    fun `delegates location mapping to LocationToDomainMapper`() {
        val locationData = buildLocationData()
        every { locationMapper.map(locationData) } returns stubLocation
        val response = PropertiesResponseDataModel(location = locationData, properties = emptyList())

        val (location, _) = mapper.map(response)

        assertEquals(stubLocation, location)
        verify(exactly = 1) { locationMapper.map(locationData) }
    }

    @Test
    fun `delegates each property mapping to PropertyToDomainMapper`() {
        val p1 = buildPropertyData(id = 1)
        val p2 = buildPropertyData(id = 2)
        every { locationMapper.map(any()) } returns stubLocation
        every { propertyMapper.map(p1) } returns stubProperty1
        every { propertyMapper.map(p2) } returns stubProperty2

        val (_, properties) =
            mapper.map(
                PropertiesResponseDataModel(
                    location = buildLocationData(),
                    properties = listOf(p1, p2),
                ),
            )

        assertEquals(listOf(stubProperty1, stubProperty2), properties)
        verify(exactly = 1) { propertyMapper.map(p1) }
        verify(exactly = 1) { propertyMapper.map(p2) }
    }

    @Test
    fun `maps empty properties list without calling property mapper`() {
        every { locationMapper.map(any()) } returns stubLocation

        val (_, properties) =
            mapper.map(
                PropertiesResponseDataModel(
                    location = buildLocationData(),
                    properties = emptyList(),
                ),
            )

        assertEquals(0, properties.size)
        verify(exactly = 0) { propertyMapper.map(any()) }
    }

    @Test
    fun `preserves property order`() {
        val dataList = (1..5).map { buildPropertyData(id = it) }
        val domainList = (1..5).map { buildDomainProperty(id = it, name = "Hostel $it") }
        every { locationMapper.map(any()) } returns stubLocation
        dataList.zip(domainList).forEach { (d, r) -> every { propertyMapper.map(d) } returns r }

        val (_, properties) =
            mapper.map(
                PropertiesResponseDataModel(
                    location = buildLocationData(),
                    properties = dataList,
                ),
            )

        assertEquals(domainList, properties)
    }

    private fun buildLocationData() =
        LocationDataModel(
            city = CityDataModel(id = 1, name = "Dublin", idCountry = 10, country = "Ireland"),
        )

    private fun buildPropertyData(id: Int) =
        PropertyDataModel(
            id = id,
            name = "Hostel $id",
            overallRating = OverallRatingDataModel(overall = 80),
            lowestPricePerNight = PriceDataModel(value = "10.00", currency = "EUR"),
        )

    private fun buildDomainProperty(
        id: Int,
        name: String,
    ) = PropertyDomainModel(
        id = id,
        name = name,
        isFeatured = false,
        rating = 8.0,
        ratingCount = "0",
        lowestPriceValue = BigDecimal("10.00"),
        lowestPriceCurrency = "EUR",
        overview = "",
        thumbnailUrl = null,
        address = "",
        type = "Hostel",
        facilities = emptyList<FacilityCategoryDomainModel>(),
        freeCancellationAvailable = false,
    )
}
