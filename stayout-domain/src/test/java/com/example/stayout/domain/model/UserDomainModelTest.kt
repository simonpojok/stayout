package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserDomainModelTest {
    private val address = AddressDomainModel("St", "Su", "Ci", "Zip", 1.0, 2.0)
    private val company = CompanyDomainModel("Corp", "Phrase", "bs")
    private val model =
        UserDomainModel(
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
        assertEquals("1-770-736-8031", model.phone)
        assertEquals("hildegard.org", model.website)
        assertEquals(address, model.address)
        assertEquals(company, model.company)
    }

    @Test
    fun `copy produces updated model`() {
        val copy = model.copy(name = "Jane Doe")
        assertEquals("Jane Doe", copy.name)
        assertEquals(model.id, copy.id)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = model.copy()
        assertEquals(model, other)
        assertEquals(model.hashCode(), other.hashCode())
    }
}
