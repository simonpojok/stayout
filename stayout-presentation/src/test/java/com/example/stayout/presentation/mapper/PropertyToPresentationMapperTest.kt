package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.model.FacilityCategoryUiModel
import com.example.stayout.presentation.provider.ResourceProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PropertyToPresentationMapperTest {
    private val resources = mockk<ResourceProvider>()
    private val facilityMapper = mockk<FacilityCategoryToPresentationMapper>()
    private val mapper = PropertyToPresentationMapper(resources, facilityMapper)

    init {
        every { resources.getString(any(), *anyVararg()) } returns "€14.18"
    }

    @Test
    fun `maps id correctly`() {
        assertEquals(42, mapper.map(buildDomain(id = 42)).id)
    }

    @Test
    fun `maps name correctly`() {
        assertEquals("Kinlay Hostel", mapper.map(buildDomain(name = "Kinlay Hostel")).name)
    }

    @Test
    fun `maps isFeatured correctly`() {
        assertTrue(mapper.map(buildDomain(isFeatured = true)).isFeatured)
        assertTrue(!mapper.map(buildDomain(isFeatured = false)).isFeatured)
    }

    @Test
    fun `formats rating to one decimal place`() {
        assertEquals("8.5", mapper.map(buildDomain(rating = 8.5)).formattedRating)
        assertEquals("7.0", mapper.map(buildDomain(rating = 7.0)).formattedRating)
    }

    @Test
    fun `maps ratingCount correctly`() {
        assertEquals("234", mapper.map(buildDomain(ratingCount = "234")).ratingCount)
    }

    @Test
    fun `maps lowestPriceEur correctly`() {
        assertEquals(BigDecimal("14.18"), mapper.map(buildDomain(price = BigDecimal("14.18"))).lowestPriceEur)
    }

    @Test
    fun `builds formattedBasePrice using ResourceProvider with EUR symbol and formatted price`() {
        val price = BigDecimal("14.18")
        every { resources.getString(any(), CurrencyDomainModel.EUR.symbol, "14.18") } returns "€14.18"

        mapper.map(buildDomain(price = price))

        verify { resources.getString(any(), CurrencyDomainModel.EUR.symbol, "14.18") }
    }

    @Test
    fun `maps overview correctly`() {
        assertEquals("Great hostel", mapper.map(buildDomain(overview = "Great hostel")).overview)
    }

    @Test
    fun `maps thumbnailUrl correctly`() {
        assertEquals(
            "https://img.com/photo.jpg",
            mapper.map(buildDomain(thumbnailUrl = "https://img.com/photo.jpg")).thumbnailUrl,
        )
    }

    @Test
    fun `thumbnailUrl can be null`() {
        assertEquals(null, mapper.map(buildDomain(thumbnailUrl = null)).thumbnailUrl)
    }

    @Test
    fun `maps address correctly`() {
        assertEquals("123 Main St", mapper.map(buildDomain(address = "123 Main St")).address)
    }

    @Test
    fun `maps type correctly`() {
        assertEquals("Hostel", mapper.map(buildDomain(type = "Hostel")).type)
    }

    @Test
    fun `delegates facility mapping to FacilityCategoryToPresentationMapper`() {
        val domainCategory =
            FacilityCategoryDomainModel(
                name = "Services",
                facilities = listOf(FacilityDomainModel(id = "FREEWIFI", name = "WiFi")),
            )
        val uiCategory = FacilityCategoryUiModel("Services", listOf("WiFi"))
        every { facilityMapper.map(domainCategory) } returns uiCategory

        val result = mapper.map(buildDomain(facilities = listOf(domainCategory)))

        assertEquals(listOf(uiCategory), result.facilities)
        verify(exactly = 1) { facilityMapper.map(domainCategory) }
    }

    @Test
    fun `maps empty facilities without calling facility mapper`() {
        val result = mapper.map(buildDomain(facilities = emptyList()))
        assertTrue(result.facilities.isEmpty())
        verify(exactly = 0) { facilityMapper.map(any()) }
    }

    @Test
    fun `maps freeCancellationAvailable correctly`() {
        assertTrue(mapper.map(buildDomain(freeCancellation = true)).freeCancellationAvailable)
        assertTrue(!mapper.map(buildDomain(freeCancellation = false)).freeCancellationAvailable)
    }

    private fun buildDomain(
        id: Int = 1,
        name: String = "Test",
        isFeatured: Boolean = false,
        rating: Double = 8.0,
        ratingCount: String = "0",
        price: BigDecimal = BigDecimal("10.00"),
        overview: String = "",
        thumbnailUrl: String? = null,
        address: String = "",
        type: String = "Hostel",
        facilities: List<FacilityCategoryDomainModel> = emptyList(),
        freeCancellation: Boolean = false,
    ) = PropertyDomainModel(
        id = id,
        name = name,
        isFeatured = isFeatured,
        rating = rating,
        ratingCount = ratingCount,
        lowestPriceValue = price,
        lowestPriceCurrency = "EUR",
        overview = overview,
        thumbnailUrl = thumbnailUrl,
        address = address,
        type = type,
        facilities = facilities,
        freeCancellationAvailable = freeCancellation,
    )
}
