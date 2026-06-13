package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PromotionDomainModelTest {
    @Test
    fun `fromString recognises MOBILE`() {
        assertEquals(PromotionType.MOBILE, PromotionType.fromString("MOBILE"))
    }

    @Test
    fun `fromString recognises APPS`() {
        assertEquals(PromotionType.APPS, PromotionType.fromString("APPS"))
    }

    @Test
    fun `fromString recognises CUSTOM`() {
        assertEquals(PromotionType.CUSTOM, PromotionType.fromString("CUSTOM"))
    }

    @Test
    fun `fromString recognises LOS`() {
        assertEquals(PromotionType.LOS, PromotionType.fromString("LOS"))
    }

    @Test
    fun `fromString returns UNKNOWN for unrecognised value`() {
        assertEquals(PromotionType.UNKNOWN, PromotionType.fromString("FLASH_SALE"))
    }

    @Test
    fun `fromString is case-insensitive`() {
        assertEquals(PromotionType.MOBILE, PromotionType.fromString("mobile"))
        assertEquals(PromotionType.APPS, PromotionType.fromString("Apps"))
        assertEquals(PromotionType.CUSTOM, PromotionType.fromString("custom"))
    }

    @Test
    fun `fromString empty string returns UNKNOWN`() {
        assertEquals(PromotionType.UNKNOWN, PromotionType.fromString(""))
    }

    @Test
    fun `PromotionDomainModel holds type label and discount`() {
        val model = PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile Deal", discount = 10)
        assertEquals(PromotionType.MOBILE, model.type)
        assertEquals("Mobile Deal", model.label)
        assertEquals(10, model.discount)
    }
}
