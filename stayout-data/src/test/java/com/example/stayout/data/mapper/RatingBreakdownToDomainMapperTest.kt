package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.RatingBreakdownDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class RatingBreakdownToDomainMapperTest {
    private val mapper = RatingBreakdownToDomainMapper()

    @Test
    fun `divides all scores by 10 and preserves ratingsCount`() {
        val result =
            mapper.map(
                RatingBreakdownDataModel(
                    security = 82,
                    location = 90,
                    staff = 75,
                    funScore = 88,
                    cleanliness = 95,
                    facilities = 70,
                    value = 85,
                    ratingsCount = 200,
                ),
            )
        assertEquals(8.2, result.security, 0.001)
        assertEquals(9.0, result.location, 0.001)
        assertEquals(7.5, result.staff, 0.001)
        assertEquals(8.8, result.funScore, 0.001)
        assertEquals(9.5, result.cleanliness, 0.001)
        assertEquals(7.0, result.facilities, 0.001)
        assertEquals(8.5, result.value, 0.001)
        assertEquals(200, result.ratingsCount)
    }

    @Test
    fun `all-zero input maps to 0 0 scores`() {
        val result = mapper.map(RatingBreakdownDataModel())
        assertEquals(0.0, result.security, 0.0)
        assertEquals(0.0, result.location, 0.0)
        assertEquals(0, result.ratingsCount)
    }

    @Test
    fun `max score 100 maps to 10 0`() {
        val result =
            mapper.map(
                RatingBreakdownDataModel(
                    security = 100,
                    location = 100,
                    staff = 100,
                    funScore = 100,
                    cleanliness = 100,
                    facilities = 100,
                    value = 100,
                ),
            )
        assertEquals(10.0, result.security, 0.001)
        assertEquals(10.0, result.value, 0.001)
    }
}
