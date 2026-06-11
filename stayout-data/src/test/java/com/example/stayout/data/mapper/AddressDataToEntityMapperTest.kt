package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.AddressDataModel
import com.example.stayout.data.remote.model.GeoDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class AddressDataToEntityMapperTest {
    private val mapper = AddressDataToEntityMapper()

    @Test
    fun `map converts data model to entity preserving geo strings`() {
        val model =
            AddressDataModel(
                street = "Kulas Light",
                suite = "Apt 556",
                city = "Gwenborough",
                zipcode = "92998-3874",
                geo = GeoDataModel(lat = "-37.3159", lng = "81.1496"),
            )

        val entity = mapper.map(model)

        assertEquals("Kulas Light", entity.street)
        assertEquals("Apt 556", entity.suite)
        assertEquals("Gwenborough", entity.city)
        assertEquals("92998-3874", entity.zipcode)
        assertEquals("-37.3159", entity.geoLat)
        assertEquals("81.1496", entity.geoLng)
    }

    @Test
    fun `map stores geo strings without parsing`() {
        val model = AddressDataModel("S", "Su", "C", "Z", GeoDataModel("0.123456789", "-0.987654321"))

        val entity = mapper.map(model)

        assertEquals("0.123456789", entity.geoLat)
        assertEquals("-0.987654321", entity.geoLng)
    }
}
