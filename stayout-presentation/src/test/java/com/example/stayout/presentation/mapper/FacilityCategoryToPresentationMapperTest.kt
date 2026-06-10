package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FacilityCategoryToPresentationMapperTest {
    private val mapper = FacilityCategoryToPresentationMapper()

    @Test
    fun `maps name correctly`() {
        val domain = FacilityCategoryDomainModel(name = "Common Areas", facilities = emptyList())
        assertEquals("Common Areas", mapper.map(domain).name)
    }

    @Test
    fun `maps facilities list correctly`() {
        val domain =
            FacilityCategoryDomainModel(
                name = "Services",
                facilities = listOf("Free WiFi", "Lockers", "24h Check-in"),
            )
        assertEquals(listOf("Free WiFi", "Lockers", "24h Check-in"), mapper.map(domain).facilities)
    }

    @Test
    fun `maps empty facilities list`() {
        val domain = FacilityCategoryDomainModel(name = "Empty", facilities = emptyList())
        assertTrue(mapper.map(domain).facilities.isEmpty())
    }
}
