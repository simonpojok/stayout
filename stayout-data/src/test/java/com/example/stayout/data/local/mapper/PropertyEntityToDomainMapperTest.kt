package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.encodeFacilities
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PropertyEntityToDomainMapperTest {
    private val mapper = PropertyEntityToDomainMapper()

    private val facilities =
        listOf(
            FacilityCategoryDomainModel(
                id = "AMENITIES",
                name = "Amenities",
                facilities =
                    listOf(
                        FacilityDomainModel(id = "WIFI", name = "WiFi"),
                        FacilityDomainModel(id = "PARKING", name = "Parking"),
                    ),
            ),
        )

    private val entity =
        PropertyEntity(
            id = 42,
            name = "Kinlay House",
            isFeatured = true,
            rating = 8.7,
            ratingCount = "1234",
            lowestPriceValue = "19.50",
            lowestPriceCurrency = "EUR",
            overview = "A great hostel in Dublin",
            thumbnailUrl = "https://example.com/thumb.jpg",
            address = "2-12 Lord Edward St",
            type = "Hostel",
            facilitiesJson = encodeFacilities(facilities),
            freeCancellationAvailable = true,
        )

    @Test
    fun `map copies all scalar fields to domain model`() {
        val domain = mapper.map(entity)

        assertEquals(42, domain.id)
        assertEquals("Kinlay House", domain.name)
        assertTrue(domain.isFeatured)
        assertEquals(8.7, domain.rating, 0.0)
        assertEquals("1234", domain.ratingCount)
        assertEquals("EUR", domain.lowestPriceCurrency)
        assertEquals("A great hostel in Dublin", domain.overview)
        assertEquals("https://example.com/thumb.jpg", domain.thumbnailUrl)
        assertEquals("2-12 Lord Edward St", domain.address)
        assertEquals("Hostel", domain.type)
        assertTrue(domain.freeCancellationAvailable)
    }

    @Test
    fun `map converts lowestPriceValue string to BigDecimal`() {
        val domain = mapper.map(entity)
        assertEquals(BigDecimal("19.50"), domain.lowestPriceValue)
    }

    @Test
    fun `map decodes facilitiesJson to domain list with id and name`() {
        val domain = mapper.map(entity)

        assertEquals(1, domain.facilities.size)
        assertEquals("AMENITIES", domain.facilities[0].id)
        assertEquals("Amenities", domain.facilities[0].name)
        assertEquals(2, domain.facilities[0].facilities.size)
        assertEquals(FacilityDomainModel(id = "WIFI", name = "WiFi"), domain.facilities[0].facilities[0])
        assertEquals(FacilityDomainModel(id = "PARKING", name = "Parking"), domain.facilities[0].facilities[1])
    }

    @Test
    fun `map handles null thumbnailUrl`() {
        val domain = mapper.map(entity.copy(thumbnailUrl = null))
        assertNull(domain.thumbnailUrl)
    }

    @Test
    fun `map handles empty facilitiesJson`() {
        val domain = mapper.map(entity.copy(facilitiesJson = "[]"))
        assertTrue(domain.facilities.isEmpty())
    }

    @Test
    fun `map preserves isFeatured false`() {
        val domain = mapper.map(entity.copy(isFeatured = false))
        assertFalse(domain.isFeatured)
    }
}
