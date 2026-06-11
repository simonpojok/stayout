package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.AddressDataModel
import com.example.stayout.data.remote.model.GeoDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class AddressToDomainMapperTest {
    private val mapper = AddressToDomainMapper()

    @Test
    fun `map converts data model to domain model with parsed coordinates`() {
        val model =
            AddressDataModel(
                street = "Kulas Light",
                suite = "Apt 556",
                city = "Gwenborough",
                zipcode = "92998-3874",
                geo = GeoDataModel(lat = "-37.3159", lng = "81.1496"),
            )

        val domain = mapper.map(model)

        assertEquals("Kulas Light", domain.street)
        assertEquals("Apt 556", domain.suite)
        assertEquals("Gwenborough", domain.city)
        assertEquals("92998-3874", domain.zipcode)
        assertEquals(-37.3159, domain.latitude, 0.0001)
        assertEquals(81.1496, domain.longitude, 0.0001)
    }

    @Test
    fun `map parses zero coordinates`() {
        val model = AddressDataModel("S", "Su", "C", "Z", GeoDataModel("0.0", "0.0"))

        val domain = mapper.map(model)

        assertEquals(0.0, domain.latitude, 0.0)
        assertEquals(0.0, domain.longitude, 0.0)
    }
}
