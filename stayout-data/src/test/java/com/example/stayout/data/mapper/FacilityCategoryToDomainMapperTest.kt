package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityCategoryDataModel
import com.example.stayout.data.remote.model.FacilityDataModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FacilityCategoryToDomainMapperTest {
    private val mapper = FacilityCategoryToDomainMapper()

    @Test
    fun `maps name correctly`() {
        val model =
            FacilityCategoryDataModel(
                name = "Common Areas",
                id = "1",
                facilities = emptyList(),
            )
        assertEquals("Common Areas", mapper.map(model).name)
    }

    @Test
    fun `maps facility names from FacilityDataModel list`() {
        val model =
            FacilityCategoryDataModel(
                name = "Services",
                id = "2",
                facilities =
                    listOf(
                        FacilityDataModel(name = "Free WiFi", id = "w1"),
                        FacilityDataModel(name = "Lockers", id = "l1"),
                    ),
            )
        val result = mapper.map(model)
        assertEquals(listOf("Free WiFi", "Lockers"), result.facilities)
    }

    @Test
    fun `maps empty facilities list`() {
        val model = FacilityCategoryDataModel(name = "Empty", id = "3", facilities = emptyList())
        assertTrue(mapper.map(model).facilities.isEmpty())
    }
}
