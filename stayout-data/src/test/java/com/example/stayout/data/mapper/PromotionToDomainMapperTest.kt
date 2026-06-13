package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.PromotionDataModel
import com.example.stayout.domain.model.PromotionType
import org.junit.Assert.assertEquals
import org.junit.Test

class PromotionToDomainMapperTest {
    private val mapper = PromotionToDomainMapper()

    @Test
    fun `maps MOBILE type label and discount`() {
        val result = mapper.map(PromotionDataModel(type = "MOBILE", name = "Mobile Deal", discount = 15))
        assertEquals(PromotionType.MOBILE, result.type)
        assertEquals("Mobile Deal", result.label)
        assertEquals(15, result.discount)
    }

    @Test
    fun `maps APPS type`() {
        assertEquals(PromotionType.APPS, mapper.map(PromotionDataModel(type = "APPS")).type)
    }

    @Test
    fun `maps CUSTOM type`() {
        assertEquals(PromotionType.CUSTOM, mapper.map(PromotionDataModel(type = "CUSTOM")).type)
    }

    @Test
    fun `maps LOS type`() {
        assertEquals(PromotionType.LOS, mapper.map(PromotionDataModel(type = "LOS")).type)
    }

    @Test
    fun `unknown type string maps to UNKNOWN`() {
        assertEquals(PromotionType.UNKNOWN, mapper.map(PromotionDataModel(type = "FLASH_SALE")).type)
    }

    @Test
    fun `type matching is case-insensitive`() {
        assertEquals(PromotionType.MOBILE, mapper.map(PromotionDataModel(type = "mobile")).type)
    }

    @Test
    fun `zero discount maps correctly`() {
        assertEquals(0, mapper.map(PromotionDataModel(type = "MOBILE", discount = 0)).discount)
    }
}
