package com.example.stayout.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class RatingBreakdownDomainModelTest {
    private val model =
        RatingBreakdownDomainModel(
            security = 8.2,
            location = 9.0,
            staff = 7.5,
            funScore = 8.8,
            cleanliness = 9.5,
            facilities = 7.0,
            value = 8.5,
            ratingsCount = 200,
        )

    @Test
    fun `stores all scores correctly`() {
        assertEquals(8.2, model.security, 0.001)
        assertEquals(9.0, model.location, 0.001)
        assertEquals(7.5, model.staff, 0.001)
        assertEquals(8.8, model.funScore, 0.001)
        assertEquals(9.5, model.cleanliness, 0.001)
        assertEquals(7.0, model.facilities, 0.001)
        assertEquals(8.5, model.value, 0.001)
        assertEquals(200, model.ratingsCount)
    }

    @Test
    fun `ratingsCount defaults to 0`() {
        val noRatings =
            RatingBreakdownDomainModel(
                security = 0.0,
                location = 0.0,
                staff = 0.0,
                funScore = 0.0,
                cleanliness = 0.0,
                facilities = 0.0,
                value = 0.0,
            )
        assertEquals(0, noRatings.ratingsCount)
    }

    @Test
    fun `copy produces independent instance with updated field`() {
        val updated = model.copy(security = 5.0)
        assertEquals(5.0, updated.security, 0.001)
        assertEquals(8.2, model.security, 0.001)
    }
}
