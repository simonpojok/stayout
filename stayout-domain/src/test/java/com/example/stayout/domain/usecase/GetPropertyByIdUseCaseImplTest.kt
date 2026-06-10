package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.math.BigDecimal

class GetPropertyByIdUseCaseImplTest {
    private val repository = mockk<PropertyRepository>()
    private val statsRepository = mockk<StatsRepository>(relaxed = true)

    private val useCase =
        GetPropertyByIdUseCaseImpl(
            repository = repository,
            statsRepository = statsRepository,
            dispatcher = Dispatchers.Unconfined,
        )

    private val stubLocation = LocationDomainModel("Dublin", "Ireland")
    private val stubProperty =
        PropertyDomainModel(
            id = 1,
            name = "Test",
            isFeatured = false,
            rating = 8.0,
            ratingCount = "0",
            lowestPriceValue = BigDecimal("10.00"),
            lowestPriceCurrency = "EUR",
            overview = "",
            thumbnailUrl = null,
            address = "",
            type = "Hostel",
            facilities = emptyList<FacilityCategoryDomainModel>(),
            freeCancellationAvailable = false,
        )

    @Test
    fun `returns the property matching the given id`() =
        runTest {
            coEvery { repository.getProperties() } returns Result.success(stubLocation to listOf(stubProperty))

            val result = useCase(1)

            assertEquals(stubProperty, result)
        }

    @Test
    fun `returns null when no property matches the given id`() =
        runTest {
            coEvery { repository.getProperties() } returns Result.success(stubLocation to listOf(stubProperty))

            val result = useCase(99)

            assertNull(result)
        }

    @Test
    fun `returns null when the repository call fails`() =
        runTest {
            coEvery { repository.getProperties() } returns Result.failure(RuntimeException("Network error"))

            val result = useCase(1)

            assertNull(result)
        }

    @Test
    fun `tracks LOAD_DETAILS stats event when the property is found`() =
        runTest {
            coEvery { repository.getProperties() } returns Result.success(stubLocation to listOf(stubProperty))

            useCase(1)

            verify { statsRepository.trackEvent(StatsEvent.LOAD_DETAILS, any()) }
        }

    @Test
    fun `tracks LOAD_DETAILS stats event when the repository call fails`() =
        runTest {
            coEvery { repository.getProperties() } returns Result.failure(RuntimeException("Network error"))

            useCase(1)

            verify { statsRepository.trackEvent(StatsEvent.LOAD_DETAILS, any()) }
        }
}
