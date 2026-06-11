package com.example.stayout.data.mapper

import com.example.stayout.data.local.entity.AddressEntity
import com.example.stayout.data.local.entity.CompanyEntity
import com.example.stayout.data.remote.model.AddressDataModel
import com.example.stayout.data.remote.model.CompanyDataModel
import com.example.stayout.data.remote.model.GeoDataModel
import com.example.stayout.data.remote.model.UserDataModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDataToEntityMapperTest {
    private val addressMapper: AddressDataToEntityMapper = mockk()
    private val companyMapper: CompanyDataToEntityMapper = mockk()
    private val mapper = UserDataToEntityMapper(addressMapper, companyMapper)

    private val addressData = AddressDataModel("St", "Su", "Ci", "Zip", GeoDataModel("1.0", "2.0"))
    private val companyData = CompanyDataModel("Corp", "Phrase", "bs")
    private val addressEntity = AddressEntity("St", "Su", "Ci", "Zip", "1.0", "2.0")
    private val companyEntity = CompanyEntity("Corp", "Phrase", "bs")

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
    fun `map converts data model to entity using sub-mappers`() {
        every { addressMapper.map(addressData) } returns addressEntity
        every { companyMapper.map(companyData) } returns companyEntity

        val entity = mapper.map(model)

        assertEquals(1, entity.id)
        assertEquals("Leanne Graham", entity.name)
        assertEquals("Bret", entity.username)
        assertEquals("Sincere@april.biz", entity.email)
        assertEquals("1-770-736-8031", entity.phone)
        assertEquals("hildegard.org", entity.website)
        assertEquals(addressEntity, entity.address)
        assertEquals(companyEntity, entity.company)
    }
}
