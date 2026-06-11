package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class GetPropertiesUseCaseImplTest {
    private val repository = mockk<PropertyRepository>()

    private val useCase =
        GetPropertiesUseCaseImpl(
            repository = repository,
            dispatcher = Dispatchers.Unconfined,
        )

    private val stubLocation = LocationDomainModel("Dublin", "Ireland")
    private val stubProperty =
        PropertyDomainModel(
            id = 1,
            name = "Test Hostel",
            isFeatured = false,
            rating = 8.0,
            ratingCount = "100",
            lowestPriceValue = BigDecimal("15.00"),
            lowestPriceCurrency = "EUR",
            overview = "A great place to stay.",
            thumbnailUrl = null,
            address = "1 Main St",
            type = "Hostel",
            facilities = emptyList<FacilityCategoryDomainModel>(),
            freeCancellationAvailable = true,
        )

    @Test
    fun `returns success result from repository`() =
        runTest {
            coEvery { repository.getProperties() } returns
                Result.success(stubLocation to listOf(stubProperty))

            val result = useCase()

            assertTrue(result.isSuccess)
            assertEquals(stubLocation to listOf(stubProperty), result.getOrNull())
        }

    @Test
    fun `propagates failure from repository`() =
        runTest {
            val exception = RuntimeException("Network error")
            coEvery { repository.getProperties() } returns Result.failure(exception)

            val result = useCase()

            assertTrue(result.isFailure)
            assertEquals("Network error", result.exceptionOrNull()?.message)
        }
}
