package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityCategoryDataModel
import com.example.stayout.data.remote.model.FacilityDataModel
import com.example.stayout.domain.model.FacilityDomainModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FacilityCategoryToDomainMapperTest {
    private val facilityMapper = mockk<FacilityToDomainMapper>()
    private val mapper = FacilityCategoryToDomainMapper(facilityMapper)

    @Test
    fun `maps id and name correctly`() {
        val model = FacilityCategoryDataModel(name = "Common Areas", id = "CAT1", facilities = emptyList())
        val result = mapper.map(model)
        assertEquals("CAT1", result.id)
        assertEquals("Common Areas", result.name)
    }

    @Test
    fun `delegates facility mapping to FacilityToDomainMapper`() {
        val dataFacility1 = FacilityDataModel(name = "Free WiFi", id = "FREEWIFI")
        val dataFacility2 = FacilityDataModel(name = "Lockers", id = "LOCKERS")
        val domainFacility1 = FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi")
        val domainFacility2 = FacilityDomainModel(id = "LOCKERS", name = "Lockers")
        every { facilityMapper.map(dataFacility1) } returns domainFacility1
        every { facilityMapper.map(dataFacility2) } returns domainFacility2

        val model =
            FacilityCategoryDataModel(
                name = "Services",
                id = "SVC",
                facilities = listOf(dataFacility1, dataFacility2),
            )
        val result = mapper.map(model)

        assertEquals(listOf(domainFacility1, domainFacility2), result.facilities)
        verify(exactly = 1) { facilityMapper.map(dataFacility1) }
        verify(exactly = 1) { facilityMapper.map(dataFacility2) }
    }

    @Test
    fun `maps empty facilities list without calling facility mapper`() {
        val model = FacilityCategoryDataModel(name = "Empty", id = "3", facilities = emptyList())
        assertTrue(mapper.map(model).facilities.isEmpty())
        verify(exactly = 0) { facilityMapper.map(any()) }
    }
}
