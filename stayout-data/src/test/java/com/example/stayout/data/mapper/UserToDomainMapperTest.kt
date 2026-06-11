package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.AddressDataModel
import com.example.stayout.data.remote.model.CompanyDataModel
import com.example.stayout.data.remote.model.GeoDataModel
import com.example.stayout.data.remote.model.UserDataModel
import com.example.stayout.domain.model.AddressDomainModel
import com.example.stayout.domain.model.CompanyDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserToDomainMapperTest {
    private val addressMapper: AddressToDomainMapper = mockk()
    private val companyMapper: CompanyToDomainMapper = mockk()
    private val mapper = UserToDomainMapper(addressMapper, companyMapper)

    private val addressData = AddressDataModel("St", "Su", "Ci", "Zip", GeoDataModel("1.0", "2.0"))
    private val companyData = CompanyDataModel("Corp", "Phrase", "bs")
    private val addressDomain = AddressDomainModel("St", "Su", "Ci", "Zip", 1.0, 2.0)
    private val companyDomain = CompanyDomainModel("Corp", "Phrase", "bs")

    private val model =
        UserDataModel(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            address = addressData,
            phone = "1-770-736-8031",
            website = "hildegard.org",
            company = companyData,
        )

    @Test
    fun `map converts data model to domain model using sub-mappers`() {
        every { addressMapper.map(addressData) } returns addressDomain
        every { companyMapper.map(companyData) } returns companyDomain

        val domain = mapper.map(model)

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
