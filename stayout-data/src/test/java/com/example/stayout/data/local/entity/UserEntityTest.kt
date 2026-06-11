package com.example.stayout.data.local.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class UserEntityTest {
    private val address = AddressEntity("1 Road", "Apt 1", "City", "00000", "0.0", "0.0")
    private val company = CompanyEntity("Corp", "Phrase", "bs")
    private val entity =
        UserEntity(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com",
            phone = "555-1234",
            website = "johndoe.com",
            address = address,
            company = company,
        )

    @Test
    fun `holds all user fields`() {
        assertEquals(1, entity.id)
        assertEquals("John Doe", entity.name)
        assertEquals("johndoe", entity.username)
        assertEquals("john@example.com", entity.email)
        assertEquals("555-1234", entity.phone)
        assertEquals("johndoe.com", entity.website)
        assertEquals(address, entity.address)
        assertEquals(company, entity.company)
    }

    @Test
    fun `copy produces updated entity`() {
        val copy = entity.copy(name = "Jane Doe")
        assertEquals("Jane Doe", copy.name)
        assertEquals(entity.id, copy.id)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = entity.copy()
        assertEquals(entity, other)
        assertEquals(entity.hashCode(), other.hashCode())
    }
}
