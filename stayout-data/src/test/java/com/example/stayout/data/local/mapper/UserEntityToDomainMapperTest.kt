package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.AddressEntity
import com.example.stayout.data.local.entity.CompanyEntity
import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.domain.model.AddressDomainModel
import com.example.stayout.domain.model.CompanyDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserEntityToDomainMapperTest {
    private val addressMapper: AddressEntityToDomainMapper = mockk()
    private val companyMapper: CompanyEntityToDomainMapper = mockk()
    private val mapper = UserEntityToDomainMapper(addressMapper, companyMapper)

    private val addressEntity = AddressEntity("St", "Su", "Ci", "Zip", "1.0", "2.0")
    private val companyEntity = CompanyEntity("Corp", "Phrase", "bs")
    private val addressDomain = AddressDomainModel("St", "Su", "Ci", "Zip", 1.0, 2.0)
    private val companyDomain = CompanyDomainModel("Corp", "Phrase", "bs")

    private val entity =
        UserEntity(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            phone = "1-770-736-8031",
            website = "hildegard.org",
            address = addressEntity,
            company = companyEntity,
        )

    @Test
    fun `map converts entity to domain model using sub-mappers`() {
        every { addressMapper.map(addressEntity) } returns addressDomain
        every { companyMapper.map(companyEntity) } returns companyDomain

        val domain = mapper.map(entity)

        assertEquals(1, domain.id)
        assertEquals("Leanne Graham", domain.name)
        assertEquals("Bret", domain.username)
        assertEquals("Sincere@april.biz", domain.email)
        assertEquals("1-770-736-8031", domain.phone)
        assertEquals("hildegard.org", domain.website)
        assertEquals(addressDomain, domain.address)
        assertEquals(companyDomain, domain.company)
    }
}
