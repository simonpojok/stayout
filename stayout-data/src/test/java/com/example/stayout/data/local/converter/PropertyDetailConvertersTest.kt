package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.domain.model.RatingBreakdownDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PropertyDetailConvertersTest {
    // region image urls

    @Test
    fun `encodeImageUrls returns empty JSON array for empty input`() {
        assertEquals("[]", encodeImageUrls(emptyList()))
    }

    @Test
    fun `round-trip preserves image urls`() {
        val urls = listOf("https://example.com/1.jpg", "https://example.com/2.jpg")
        assertEquals(urls, decodeImageUrls(encodeImageUrls(urls)))
    }

    @Test
    fun `decodeImageUrls returns empty list for empty JSON array`() {
        assertTrue(decodeImageUrls("[]").isEmpty())
    }

    // endregion

    // region rating breakdown

    private val ratingBreakdown =
        RatingBreakdownDomainModel(
            security = 9.0,
            location = 8.5,
            staff = 9.2,
            funScore = 8.8,
            cleanliness = 9.1,
            facilities = 8.7,
            value = 8.4,
            ratingsCount = 123,
        )

    @Test
    fun `round-trip preserves rating breakdown`() {
        assertEquals(ratingBreakdown, decodeRatingBreakdown(encodeRatingBreakdown(ratingBreakdown)))
    }

    @Test
    fun `encodeRatingBreakdown produces JSON containing field values`() {
        val json = encodeRatingBreakdown(ratingBreakdown)
        assertTrue(json.contains("9.0"))
        assertTrue(json.contains("123"))
    }

    // endregion

    // region promotions

    @Test
    fun `encodePromotions returns empty JSON array for empty input`() {
        assertEquals("[]", encodePromotions(emptyList()))
    }

    @Test
    fun `round-trip preserves promotions`() {
        val promotions =
            listOf(
                PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile discount", discount = 10),
                PromotionDomainModel(type = PromotionType.LOS, label = "Length of stay", discount = 15),
            )

        assertEquals(promotions, decodePromotions(encodePromotions(promotions)))
    }

    @Test
    fun `decodePromotions maps unknown type string to UNKNOWN`() {
        val json = """[{"type":"SOME_NEW_TYPE","label":"New promo","discount":5}]"""

        val decoded = decodePromotions(json)

        assertEquals(1, decoded.size)
        assertEquals(PromotionType.UNKNOWN, decoded[0].type)
        assertEquals("New promo", decoded[0].label)
        assertEquals(5, decoded[0].discount)
    }

    @Test
    fun `decodePromotions returns empty list for empty JSON array`() {
        assertTrue(decodePromotions("[]").isEmpty())
    }

    // endregion
}
