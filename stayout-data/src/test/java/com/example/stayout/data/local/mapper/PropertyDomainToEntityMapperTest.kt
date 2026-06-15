package com.example.stayout.data.local.mapper

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.model.RatingBreakdownDomainModel
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
            imageUrls = listOf("https://example.com/1.jpg", "https://example.com/2.jpg"),
            address = "2-12 Lord Edward St",
            type = "Hostel",
            facilities =
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
                ),
            freeCancellationAvailable = true,
            latitude = 53.3441,
            longitude = -6.2675,
            ratingBreakdown =
                RatingBreakdownDomainModel(
                    security = 9.0,
                    location = 8.5,
                    staff = 9.2,
                    funScore = 8.8,
                    cleanliness = 9.1,
                    facilities = 8.7,
                    value = 8.4,
                    ratingsCount = 1234,
                ),
            distanceKm = 1.2,
            isNew = true,
            veryPopular = true,
            dormPriceValue = BigDecimal("15.00"),
            privatePriceValue = BigDecimal("45.00"),
            promotions =
                listOf(PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile discount", discount = 10)),
            averagePriceValue = BigDecimal("22.00"),
            originalPriceValue = BigDecimal("25.00"),
            totalDiscount = BigDecimal("3.00"),
            district = "Temple Bar",
            isRecommended = true,
            starRating = 3,
            freeCancellationUntil = "2024-12-31",
            minimumStayDescription = "Minimum stay 2 nights",
            lastUpdatedAt = 1_700_000_000_000L,
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

    @Test
    fun `map copies lastUpdatedAt to entity`() {
        val entity = mapper.map(domain)
        assertEquals(1_700_000_000_000L, entity.lastUpdatedAt)
    }

    @Test
    fun `map defaults lastUpdatedAt to zero when null`() {
        val entity = mapper.map(domain.copy(lastUpdatedAt = null))
        assertEquals(0L, entity.lastUpdatedAt)
    }

    @Test
    fun `map encodes imageUrls as JSON string`() {
        val entity = mapper.map(domain)

        assertTrue(entity.imageUrlsJson.contains("https://example.com/1.jpg"))
        assertTrue(entity.imageUrlsJson.contains("https://example.com/2.jpg"))
    }

    @Test
    fun `map encodes empty imageUrls as empty JSON array`() {
        val entity = mapper.map(domain.copy(imageUrls = emptyList()))
        assertEquals("[]", entity.imageUrlsJson)
    }

    @Test
    fun `map copies latitude and longitude`() {
        val entity = mapper.map(domain)

        assertEquals(53.3441, entity.latitude, 0.0)
        assertEquals(-6.2675, entity.longitude, 0.0)
    }

    @Test
    fun `map encodes ratingBreakdown as JSON string when present`() {
        val entity = mapper.map(domain)

        assertTrue(requireNotNull(entity.ratingBreakdownJson).contains("1234"))
    }

    @Test
    fun `map sets ratingBreakdownJson to null when ratingBreakdown is absent`() {
        val entity = mapper.map(domain.copy(ratingBreakdown = null))
        assertNull(entity.ratingBreakdownJson)
    }

    @Test
    fun `map copies distanceKm, isNew and veryPopular`() {
        val entity = mapper.map(domain)

        assertEquals(1.2, entity.distanceKm)
        assertTrue(entity.isNew)
        assertTrue(entity.veryPopular)
    }

    @Test
    fun `map converts dormPriceValue and privatePriceValue to plain strings`() {
        val entity = mapper.map(domain)

        assertEquals("15.00", entity.dormPriceValue)
        assertEquals("45.00", entity.privatePriceValue)
    }

    @Test
    fun `map handles null dormPriceValue and privatePriceValue`() {
        val entity = mapper.map(domain.copy(dormPriceValue = null, privatePriceValue = null))

        assertNull(entity.dormPriceValue)
        assertNull(entity.privatePriceValue)
    }

    @Test
    fun `map encodes promotions as JSON string`() {
        val entity = mapper.map(domain)
        assertTrue(entity.promotionsJson.contains("Mobile discount"))
    }

    @Test
    fun `map encodes empty promotions as empty JSON array`() {
        val entity = mapper.map(domain.copy(promotions = emptyList()))
        assertEquals("[]", entity.promotionsJson)
    }

    @Test
    fun `map converts averagePriceValue, originalPriceValue and totalDiscount to plain strings`() {
        val entity = mapper.map(domain)

        assertEquals("22.00", entity.averagePriceValue)
        assertEquals("25.00", entity.originalPriceValue)
        assertEquals("3.00", entity.totalDiscount)
    }

    @Test
    fun `map copies district, isRecommended, starRating, freeCancellationUntil and minimumStayDescription`() {
        val entity = mapper.map(domain)

        assertEquals("Temple Bar", entity.district)
        assertTrue(entity.isRecommended)
        assertEquals(3, entity.starRating)
        assertEquals("2024-12-31", entity.freeCancellationUntil)
        assertEquals("Minimum stay 2 nights", entity.minimumStayDescription)
    }
}
