package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.encodeFacilities
import com.example.stayout.data.local.converter.encodeImageUrls
import com.example.stayout.data.local.converter.encodePromotions
import com.example.stayout.data.local.converter.encodeRatingBreakdown
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.domain.model.RatingBreakdownDomainModel
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

    private val ratingBreakdown =
        RatingBreakdownDomainModel(
            security = 9.0,
            location = 8.5,
            staff = 9.2,
            funScore = 8.8,
            cleanliness = 9.1,
            facilities = 8.7,
            value = 8.4,
            ratingsCount = 1234,
        )

    private val promotions =
        listOf(PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile discount", discount = 10))

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
            imageUrlsJson = encodeImageUrls(listOf("https://example.com/1.jpg", "https://example.com/2.jpg")),
            address = "2-12 Lord Edward St",
            type = "Hostel",
            facilitiesJson = encodeFacilities(facilities),
            freeCancellationAvailable = true,
            latitude = 53.3441,
            longitude = -6.2675,
            ratingBreakdownJson = encodeRatingBreakdown(ratingBreakdown),
            distanceKm = 1.2,
            isNew = true,
            veryPopular = true,
            dormPriceValue = "15.00",
            privatePriceValue = "45.00",
            promotionsJson = encodePromotions(promotions),
            averagePriceValue = "22.00",
            originalPriceValue = "25.00",
            totalDiscount = "3.00",
            district = "Temple Bar",
            isRecommended = true,
            starRating = 3,
            freeCancellationUntil = "2024-12-31",
            minimumStayDescription = "Minimum stay 2 nights",
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

    @Test
    fun `map copies lastUpdatedAt to domain model`() {
        val domain = mapper.map(entity.copy(lastUpdatedAt = 1_700_000_000_000L))
        assertEquals(1_700_000_000_000L, domain.lastUpdatedAt)
    }

    @Test
    fun `map returns null lastUpdatedAt when entity value is zero`() {
        val domain = mapper.map(entity.copy(lastUpdatedAt = 0L))
        assertNull(domain.lastUpdatedAt)
    }

    @Test
    fun `map decodes imageUrlsJson to domain list`() {
        val domain = mapper.map(entity)
        assertEquals(listOf("https://example.com/1.jpg", "https://example.com/2.jpg"), domain.imageUrls)
    }

    @Test
    fun `map handles empty imageUrlsJson`() {
        val domain = mapper.map(entity.copy(imageUrlsJson = "[]"))
        assertTrue(domain.imageUrls.isEmpty())
    }

    @Test
    fun `map copies latitude and longitude to domain model`() {
        val domain = mapper.map(entity)

        assertEquals(53.3441, domain.latitude, 0.0)
        assertEquals(-6.2675, domain.longitude, 0.0)
    }

    @Test
    fun `map decodes ratingBreakdownJson to domain model`() {
        val domain = mapper.map(entity)
        assertEquals(ratingBreakdown, domain.ratingBreakdown)
    }

    @Test
    fun `map returns null ratingBreakdown when ratingBreakdownJson is null`() {
        val domain = mapper.map(entity.copy(ratingBreakdownJson = null))
        assertNull(domain.ratingBreakdown)
    }

    @Test
    fun `map copies distanceKm, isNew and veryPopular to domain model`() {
        val domain = mapper.map(entity)

        assertEquals(1.2, domain.distanceKm)
        assertTrue(domain.isNew)
        assertTrue(domain.veryPopular)
    }

    @Test
    fun `map converts dormPriceValue and privatePriceValue to BigDecimal`() {
        val domain = mapper.map(entity)

        assertEquals(BigDecimal("15.00"), domain.dormPriceValue)
        assertEquals(BigDecimal("45.00"), domain.privatePriceValue)
    }

    @Test
    fun `map handles null dormPriceValue and privatePriceValue`() {
        val domain = mapper.map(entity.copy(dormPriceValue = null, privatePriceValue = null))

        assertNull(domain.dormPriceValue)
        assertNull(domain.privatePriceValue)
    }

    @Test
    fun `map decodes promotionsJson to domain list`() {
        val domain = mapper.map(entity)
        assertEquals(promotions, domain.promotions)
    }

    @Test
    fun `map handles empty promotionsJson`() {
        val domain = mapper.map(entity.copy(promotionsJson = "[]"))
        assertTrue(domain.promotions.isEmpty())
    }

    @Test
    fun `map converts averagePriceValue, originalPriceValue and totalDiscount to BigDecimal`() {
        val domain = mapper.map(entity)

        assertEquals(BigDecimal("22.00"), domain.averagePriceValue)
        assertEquals(BigDecimal("25.00"), domain.originalPriceValue)
        assertEquals(BigDecimal("3.00"), domain.totalDiscount)
    }

    @Test
    fun `map copies district, isRecommended, starRating, freeCancellationUntil and minimumStayDescription`() {
        val domain = mapper.map(entity)

        assertEquals("Temple Bar", domain.district)
        assertTrue(domain.isRecommended)
        assertEquals(3, domain.starRating)
        assertEquals("2024-12-31", domain.freeCancellationUntil)
        assertEquals("Minimum stay 2 nights", domain.minimumStayDescription)
    }
}
