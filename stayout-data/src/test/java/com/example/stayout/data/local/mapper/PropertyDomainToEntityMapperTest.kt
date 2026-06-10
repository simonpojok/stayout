package com.example.stayout.data.local.mapper

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PropertyDomainToEntityMapperTest {
    private val mapper = PropertyDomainToEntityMapper()

    private val domain =
        PropertyDomainModel(
            id = 42,
            name = "Kinlay House",
            isFeatured = true,
            rating = 8.7,
            ratingCount = "1234",
            lowestPriceValue = BigDecimal("19.50"),
            lowestPriceCurrency = "EUR",
            overview = "A great hostel in Dublin",
            thumbnailUrl = "https://example.com/thumb.jpg",
            address = "2-12 Lord Edward St",
            type = "Hostel",
            facilities =
                listOf(
                    FacilityCategoryDomainModel("Amenities", listOf("WiFi", "Parking")),
                ),
            freeCancellationAvailable = true,
        )

    @Test
    fun `map copies all scalar fields to entity`() {
        val entity = mapper.map(domain)

        assertEquals(42, entity.id)
        assertEquals("Kinlay House", entity.name)
        assertTrue(entity.isFeatured)
        assertEquals(8.7, entity.rating, 0.0)
        assertEquals("1234", entity.ratingCount)
        assertEquals("EUR", entity.lowestPriceCurrency)
        assertEquals("A great hostel in Dublin", entity.overview)
        assertEquals("https://example.com/thumb.jpg", entity.thumbnailUrl)
        assertEquals("2-12 Lord Edward St", entity.address)
        assertEquals("Hostel", entity.type)
        assertTrue(entity.freeCancellationAvailable)
    }

    @Test
    fun `map converts lowestPriceValue to plain string`() {
        val entity = mapper.map(domain)
        assertEquals("19.50", entity.lowestPriceValue)
    }

    @Test
    fun `map encodes facilities as JSON string`() {
        val entity = mapper.map(domain)

        assertTrue(entity.facilitiesJson.contains("Amenities"))
        assertTrue(entity.facilitiesJson.contains("WiFi"))
        assertTrue(entity.facilitiesJson.contains("Parking"))
    }

    @Test
    fun `map handles null thumbnailUrl`() {
        val entity = mapper.map(domain.copy(thumbnailUrl = null))
        assertNull(entity.thumbnailUrl)
    }

    @Test
    fun `map handles empty facilities list`() {
        val entity = mapper.map(domain.copy(facilities = emptyList()))
        assertEquals("[]", entity.facilitiesJson)
    }

    @Test
    fun `map preserves isFeatured false`() {
        val entity = mapper.map(domain.copy(isFeatured = false))
        assertFalse(entity.isFeatured)
    }
}
