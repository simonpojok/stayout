package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.InternetConnectionError
import com.example.stayout.domain.model.InternetConnectionException
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class GetPropertyByIdUseCaseImplTest {
    private val repository = mockk<PropertyRepository>()
    private val useCase =
        GetPropertyByIdUseCaseImpl(
            repository = repository,
            dispatcher = Dispatchers.Unconfined,
        )

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
    fun `returns the property from repository`() =
        runTest {
            coEvery { repository.getPropertyById(1) } returns Result.success(stubProperty)

            val result = useCase(1)

            assertEquals(stubProperty, result.getOrNull())
        }

    @Test
    fun `returns null when repository returns null`() =
        runTest {
            coEvery { repository.getPropertyById(99) } returns Result.success(null)

            val result = useCase(99)

            assertNull(result.getOrNull())
        }

    @Test
    fun `returns failure when repository fails`() =
        runTest {
            val exception = InternetConnectionException(InternetConnectionError.NoConnection)
            coEvery { repository.getPropertyById(1) } returns Result.failure(exception)

            val result = useCase(1)

            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }

    @Test
    fun `works with default IO dispatcher`() =
        runTest {
            coEvery { repository.getPropertyById(1) } returns Result.success(stubProperty)

            val result = GetPropertyByIdUseCaseImpl(repository)(1)

            assertEquals(stubProperty, result.getOrNull())
        }
}
