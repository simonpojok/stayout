package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FacilitiesConverterTest {
    private fun facility(
        id: String,
        name: String,
    ) = FacilityDomainModel(id = id, name = name)

    @Test
    fun `encodeFacilities produces valid JSON with name and items`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(
                    id = "CAT1",
                    name = "Amenities",
                    facilities = listOf(facility("WIFI", "WiFi"), facility("PARKING", "Parking")),
                ),
            )

        val json = encodeFacilities(categories)

        assertTrue(json.contains("Amenities"))
        assertTrue(json.contains("WiFi"))
        assertTrue(json.contains("Parking"))
        assertTrue(json.contains("WIFI"))
    }

    @Test
    fun `decodeFacilities parses all categories and their facilities`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(
                    id = "CAT1",
                    name = "Amenities",
                    facilities = listOf(facility("WIFI", "WiFi"), facility("POOL", "Pool")),
                ),
                FacilityCategoryDomainModel(
                    id = "CAT2",
                    name = "Services",
                    facilities = listOf(facility("BREAKFAST", "Breakfast")),
                ),
            )

        val decoded = decodeFacilities(encodeFacilities(categories))

        assertEquals(2, decoded.size)
        assertEquals("Amenities", decoded[0].name)
        assertEquals(listOf(facility("WIFI", "WiFi"), facility("POOL", "Pool")), decoded[0].facilities)
        assertEquals("Services", decoded[1].name)
        assertEquals(listOf(facility("BREAKFAST", "Breakfast")), decoded[1].facilities)
    }

    @Test
    fun `round-trip preserves an empty facilities list`() {
        val categories =
            listOf(
                FacilityCategoryDomainModel(id = "CAT1", name = "Empty", facilities = emptyList()),
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
                FacilityCategoryDomainModel(
                    id = "BATH",
                    name = "Bathroom",
                    facilities = listOf(facility("T", "Towels"), facility("S", "Shampoo"), facility("H", "Hairdryer")),
                ),
                FacilityCategoryDomainModel(
                    id = "KITCH",
                    name = "Kitchen",
                    facilities = listOf(facility("M", "Microwave"), facility("F", "Fridge")),
                ),
                FacilityCategoryDomainModel(
                    id = "ENT",
                    name = "Entertainment",
                    facilities = listOf(facility("TV", "TV")),
                ),
            )

        assertEquals(categories, decodeFacilities(encodeFacilities(categories)))
    }

    @Test
    fun `decodeFacilities handles old string-only format for backwards compatibility`() {
        // Old format stored facilities as plain strings inside the items array
        val oldJson = """[{"name":"Amenities","items":["WiFi","Parking"]}]"""

        val decoded = decodeFacilities(oldJson)

        assertEquals(1, decoded.size)
        assertEquals("Amenities", decoded[0].name)
        assertEquals(2, decoded[0].facilities.size)
        assertEquals("", decoded[0].facilities[0].id)
        assertEquals("WiFi", decoded[0].facilities[0].name)
        assertEquals("Parking", decoded[0].facilities[1].name)
    }
}
