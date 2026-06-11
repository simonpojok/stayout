package com.example.stayout.data.remote.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserDataModelTest {
    private val geo = GeoDataModel(lat = "-37.3159", lng = "81.1496")
    private val address = AddressDataModel("Kulas Light", "Apt 556", "Gwenborough", "92998-3874", geo)
    private val company = CompanyDataModel("Romaguera-Crona", "Multi-layered neural-net", "harness e-markets")
    private val model =
        UserDataModel(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            address = address,
            phone = "1-770-736-8031",
            website = "hildegard.org",
            company = company,
        )

    @Test
    fun `holds all user fields`() {
        assertEquals(1, model.id)
        assertEquals("Leanne Graham", model.name)
        assertEquals("Bret", model.username)
        assertEquals("Sincere@april.biz", model.email)
        assertEquals(address, model.address)
        assertEquals("1-770-736-8031", model.phone)
        assertEquals("hildegard.org", model.website)
        assertEquals(company, model.company)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }

    @Test
    fun `copy produces updated model`() {
        val copy = model.copy(name = "Jane Doe")
        assertEquals("Jane Doe", copy.name)
        assertEquals(model.id, copy.id)
    }
}
