package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityCategoryDataModel
import com.example.stayout.data.remote.model.FacilityDataModel
import com.example.stayout.data.remote.model.ImageGalleryDataModel
import com.example.stayout.data.remote.model.OverallRatingDataModel
import com.example.stayout.data.remote.model.PriceDataModel
import com.example.stayout.data.remote.model.PropertyDataModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PropertyToDomainMapperTest {
    private val facilityCategoryMapper = mockk<FacilityCategoryToDomainMapper>()
    private val ratingBreakdownMapper = mockk<RatingBreakdownToDomainMapper>()
    private val promotionMapper = mockk<PromotionToDomainMapper>()
    private val mapper = PropertyToDomainMapper(facilityCategoryMapper, ratingBreakdownMapper, promotionMapper)

    @Test
    fun `maps id and name correctly`() {
        val result = mapper.map(buildProperty(id = 42, name = "Kinlay Hostel"))
        assertEquals(42, result.id)
        assertEquals("Kinlay Hostel", result.name)
    }

    @Test
    fun `maps isFeatured true correctly`() {
        assertTrue(mapper.map(buildProperty(isFeatured = true)).isFeatured)
    }

    @Test
    fun `maps isFeatured false correctly`() {
        assertTrue(!mapper.map(buildProperty(isFeatured = false)).isFeatured)
    }

    @Test
    fun `calculates rating as overall divided by 10`() {
        val result = mapper.map(buildProperty(overall = 85))
        assertEquals(8.5, result.rating, 0.001)
    }

    @Test
    fun `maps ratingCount from numberOfRatings`() {
        assertEquals("234", mapper.map(buildProperty(numberOfRatings = "234")).ratingCount)
    }

    @Test
    fun `maps valid price string to BigDecimal`() {
        assertEquals(BigDecimal("14.18"), mapper.map(buildProperty(priceValue = "14.18")).lowestPriceValue)
    }

    @Test
    fun `maps invalid price string to BigDecimal ZERO`() {
        assertEquals(BigDecimal.ZERO, mapper.map(buildProperty(priceValue = "N/A")).lowestPriceValue)
    }

    @Test
    fun `maps empty price string to BigDecimal ZERO`() {
        assertEquals(BigDecimal.ZERO, mapper.map(buildProperty(priceValue = "")).lowestPriceValue)
    }

    @Test
    fun `maps currency correctly`() {
        assertEquals("EUR", mapper.map(buildProperty(currency = "EUR")).lowestPriceCurrency)
    }

    @Test
    fun `uses first gallery image as thumbnailUrl`() {
        val gallery =
            listOf(
                ImageGalleryDataModel(prefix = "img.com/", suffix = "first.jpg"),
                ImageGalleryDataModel(prefix = "img.com/", suffix = "second.jpg"),
            )
        assertEquals("https://img.com/first.jpg", mapper.map(buildProperty(gallery = gallery)).thumbnailUrl)
    }

    @Test
    fun `thumbnailUrl is null when gallery is empty`() {
        assertNull(mapper.map(buildProperty(gallery = emptyList())).thumbnailUrl)
    }

    @Test
    fun `builds address from address1 only when address2 is null`() {
        assertEquals("123 Main St", mapper.map(buildProperty(address1 = "123 Main St", address2 = null)).address)
    }

    @Test
    fun `builds address with comma when address2 is present`() {
        assertEquals(
            "123 Main St, Dublin 2",
            mapper.map(buildProperty(address1 = "123 Main St", address2 = "Dublin 2")).address,
        )
    }

    @Test
    fun `address2 blank is excluded from address`() {
        assertEquals("123 Main St", mapper.map(buildProperty(address1 = "123 Main St", address2 = "  ")).address)
    }

    @Test
    fun `maps type correctly`() {
        assertEquals("Hostel", mapper.map(buildProperty(type = "Hostel")).type)
    }

    @Test
    fun `delegates facility mapping to FacilityCategoryToDomainMapper`() {
        val dataCategory =
            FacilityCategoryDataModel(
                name = "Services",
                id = "s1",
                facilities = listOf(FacilityDataModel("Free WiFi", "FREEWIFI")),
            )
        val domainCategory =
            FacilityCategoryDomainModel(
                id = "s1",
                name = "Services",
                facilities = listOf(FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi")),
            )
        every { facilityCategoryMapper.map(dataCategory) } returns domainCategory

        val result = mapper.map(buildProperty(facilities = listOf(dataCategory)))

        assertEquals(listOf(domainCategory), result.facilities)
        verify(exactly = 1) { facilityCategoryMapper.map(dataCategory) }
    }

    @Test
    fun `maps empty facilities list without calling mapper`() {
        val result = mapper.map(buildProperty(facilities = emptyList()))
        assertTrue(result.facilities.isEmpty())
        verify(exactly = 0) { facilityCategoryMapper.map(any()) }
    }

    @Test
    fun `maps freeCancellationAvailable correctly`() {
        assertTrue(mapper.map(buildProperty(freeCancellation = true)).freeCancellationAvailable)
        assertTrue(!mapper.map(buildProperty(freeCancellation = false)).freeCancellationAvailable)
    }

    private fun buildProperty(
        id: Int = 1,
        name: String = "Test",
        isFeatured: Boolean = false,
        overall: Int = 80,
        numberOfRatings: String = "0",
        priceValue: String = "10.00",
        currency: String = "EUR",
        overview: String = "",
        gallery: List<ImageGalleryDataModel> = emptyList(),
        address1: String = "",
        address2: String? = null,
        type: String = "Hostel",
        facilities: List<FacilityCategoryDataModel> = emptyList(),
        freeCancellation: Boolean = false,
    ) = PropertyDataModel(
        id = id,
        name = name,
        isFeatured = isFeatured,
        overallRating = OverallRatingDataModel(overall = overall, numberOfRatings = numberOfRatings),
        lowestPricePerNight = PriceDataModel(value = priceValue, currency = currency),
        overview = overview,
        imagesGallery = gallery,
        address1 = address1,
        address2 = address2,
        type = type,
        facilities = facilities,
        freeCancellationAvailable = freeCancellation,
    )
}
