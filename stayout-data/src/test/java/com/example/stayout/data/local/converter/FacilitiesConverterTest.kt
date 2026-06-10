package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FacilitiesConverterTest {
    @Test
    fun `encodeFacilities produces valid JSON with name and items`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(name = "Amenities", facilities = listOf("WiFi", "Parking")),
            )

        val json = encodeFacilities(categories)

        assertTrue(json.contains("Amenities"))
        assertTrue(json.contains("WiFi"))
        assertTrue(json.contains("Parking"))
    }

    @Test
    fun `decodeFacilities parses all categories and their facilities`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(name = "Amenities", facilities = listOf("WiFi", "Pool")),
                FacilityCategoryDomainModel(name = "Services", facilities = listOf("Breakfast")),
            )

        val decoded = decodeFacilities(encodeFacilities(categories))

        assertEquals(2, decoded.size)
        assertEquals("Amenities", decoded[0].name)
        assertEquals(listOf("WiFi", "Pool"), decoded[0].facilities)
        assertEquals("Services", decoded[1].name)
        assertEquals(listOf("Breakfast"), decoded[1].facilities)
    }

    @Test
    fun `round-trip preserves an empty facilities list`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(name = "Empty", facilities = emptyList()),
            )

        val decoded = decodeFacilities(encodeFacilities(categories))

        assertEquals(1, decoded.size)
        assertTrue(decoded[0].facilities.isEmpty())
    }

    @Test
    fun `encodeFacilities returns empty JSON array for empty input`() {
        val json = encodeFacilities(emptyList())
        assertEquals("[]", json)
    }

    @Test
    fun `decodeFacilities returns empty list for empty JSON array`() {
        val result = decodeFacilities("[]")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `round-trip preserves multiple categories with multiple items each`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel("Bathroom", listOf("Towels", "Shampoo", "Hairdryer")),
                FacilityCategoryDomainModel("Kitchen", listOf("Microwave", "Fridge")),
                FacilityCategoryDomainModel("Entertainment", listOf("TV")),
            )

        assertEquals(categories, decodeFacilities(encodeFacilities(categories)))
    }
}
