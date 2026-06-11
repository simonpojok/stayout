package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.AddressEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class AddressEntityToDomainMapperTest {
    private val mapper = AddressEntityToDomainMapper()

    @Test
    fun `map converts entity strings to domain model with parsed coordinates`() {
        val entity =
            AddressEntity(
                street = "Kulas Light",
                suite = "Apt 556",
                city = "Gwenborough",
                zipcode = "92998-3874",
                geoLat = "-37.3159",
                geoLng = "81.1496",
            )

        val domain = mapper.map(entity)

        assertEquals("Kulas Light", domain.street)
        assertEquals("Apt 556", domain.suite)
        assertEquals("Gwenborough", domain.city)
        assertEquals("92998-3874", domain.zipcode)
        assertEquals(-37.3159, domain.latitude, 0.0001)
        assertEquals(81.1496, domain.longitude, 0.0001)
    }

    @Test
    fun `map parses zero coordinates`() {
        val entity = AddressEntity("S", "Su", "C", "Z", "0.0", "0.0")

        val domain = mapper.map(entity)

        assertEquals(0.0, domain.latitude, 0.0)
        assertEquals(0.0, domain.longitude, 0.0)
    }

    @Test
    fun `map parses negative coordinates`() {
        val entity = AddressEntity("S", "Su", "C", "Z", "-90.0", "-180.0")

        val domain = mapper.map(entity)

        assertEquals(-90.0, domain.latitude, 0.0)
        assertEquals(-180.0, domain.longitude, 0.0)
    }
}
