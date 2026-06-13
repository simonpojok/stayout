package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
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
    fun `maps facility names from FacilityDomainModel list`() {
        val domain =
            FacilityCategoryDomainModel(
                name = "Services",
                facilities =
                    listOf(
                        FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi"),
                        FacilityDomainModel(id = "LOCKERS", name = "Lockers"),
                        FacilityDomainModel(id = "24HOURRECEPTION", name = "24h Check-in"),
                    ),
            )
        assertEquals(listOf("Free WiFi", "Lockers", "24h Check-in"), mapper.map(domain).facilities)
    }

    @Test
    fun `maps empty facilities list`() {
        val domain = FacilityCategoryDomainModel(name = "Empty", facilities = emptyList())
        assertTrue(mapper.map(domain).facilities.isEmpty())
    }
}
