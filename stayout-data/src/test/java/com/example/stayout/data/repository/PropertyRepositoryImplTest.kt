package com.example.stayout.data.repository

import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.local.entity.LocationEntity
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.data.local.mapper.LocationDomainToEntityMapper
import com.example.stayout.data.local.mapper.LocationEntityToDomainMapper
import com.example.stayout.data.local.mapper.PropertyDomainToEntityMapper
import com.example.stayout.data.local.mapper.PropertyEntityToDomainMapper
import com.example.stayout.data.mapper.PropertiesResponseToDomainMapper
import com.example.stayout.data.mapper.ThrowableToInternetConnectionErrorMapper
import com.example.stayout.data.remote.api.PropertyApi
import com.example.stayout.data.remote.model.CityDataModel
import com.example.stayout.data.remote.model.LocationDataModel
import com.example.stayout.data.remote.model.PropertiesResponseDataModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.InternetConnectionError
import com.example.stayout.domain.model.InternetConnectionException
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PropertyRepositoryImplTest {
    private val api = mockk<PropertyApi>()
    private val mapper = mockk<PropertiesResponseToDomainMapper>()
    private val statsRepository = mockk<StatsRepository>(relaxed = true)
    private val propertyDao = mockk<PropertyDao>(relaxed = true)
    private val locationDao = mockk<LocationDao>(relaxed = true)
    private val propertyEntityToDomain = mockk<PropertyEntityToDomainMapper>()
    private val propertyDomainToEntity = mockk<PropertyDomainToEntityMapper>()
    private val locationEntityToDomain = mockk<LocationEntityToDomainMapper>()
    private val locationDomainToEntity = mockk<LocationDomainToEntityMapper>()
    private val throwableToInternetConnectionError = mockk<ThrowableToInternetConnectionErrorMapper>()

    private val repository =
        PropertyRepositoryImpl(
            api = api,
            mapper = mapper,
            statsRepository = statsRepository,
            propertyDao = propertyDao,
            locationDao = locationDao,
            propertyEntityToDomain = propertyEntityToDomain,
            propertyDomainToEntity = propertyDomainToEntity,
            locationEntityToDomain = locationEntityToDomain,
            locationDomainToEntity = locationDomainToEntity,
            throwableToInternetConnectionError = throwableToInternetConnectionError,
        )

    private val stubResponse =
        PropertiesResponseDataModel(
            location = LocationDataModel(city = CityDataModel(1, "Dublin", 10, "Ireland")),
            properties = emptyList(),
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
    private val stubResult = stubLocation to listOf(stubProperty)

    private val stubLocationEntity = LocationEntity(cityName = "Dublin", countryName = "Ireland")
    private val stubPropertyEntity =
        PropertyEntity(
            id = 1,
            name = "Test",
            isFeatured = false,
            rating = 8.0,
            ratingCount = "0",
            lowestPriceValue = "10.00",
            lowestPriceCurrency = "EUR",
            overview = "",
            thumbnailUrl = null,
            address = "",
            type = "Hostel",
            facilitiesJson = "[]",
            freeCancellationAvailable = false,
        )

    init {
        every { locationDomainToEntity.map(stubLocation) } returns stubLocationEntity
        every { propertyDomainToEntity.map(stubProperty) } returns stubPropertyEntity
        coEvery { locationDao.get() } returns null
        coEvery { propertyDao.getById(any()) } returns null
    }

    @Test
    fun `returns success with mapped result on successful API call`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            val result = repository.getProperties()

            assertTrue(result.isSuccess)
            assertEquals(stubResult, result.getOrNull())
        }

    @Test
    fun `tracks LOAD_PROPERTIES stats event on success`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            repository.getProperties()

            verify { statsRepository.trackEvent(StatsEvent.LOAD_PROPERTIES, any()) }
        }

    @Test
    fun `calls API again on subsequent call`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            repository.getProperties()
            val second = repository.getProperties()

            assertTrue(second.isSuccess)
            assertEquals(stubResult, second.getOrNull())
            coVerify(exactly = 2) { api.getProperties() }
        }

    @Test
    fun `returns failure when API throws and no cache exists`() =
        runTest {
            val exception = RuntimeException("Network error")
            coEvery { api.getProperties() } throws exception
            every { throwableToInternetConnectionError.map(exception) } returns InternetConnectionError.Unknown

            val result = repository.getProperties()

            assertTrue(result.isFailure)
            val failure = result.exceptionOrNull()
            assertTrue(failure is InternetConnectionException)
            assertEquals(InternetConnectionError.Unknown, (failure as InternetConnectionException).error)
            assertEquals(exception, failure.cause)
        }

    @Test
    fun `returns cached result when API throws after a successful call`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult
            repository.getProperties()

            coEvery { api.getProperties() } throws RuntimeException("Network error")
            coEvery { locationDao.get() } returns stubLocationEntity
            every { locationEntityToDomain.map(stubLocationEntity) } returns stubLocation
            coEvery { propertyDao.getAll() } returns listOf(stubPropertyEntity)
            every { propertyEntityToDomain.map(stubPropertyEntity) } returns stubProperty

            val result = repository.getProperties()

            assertTrue(result.isSuccess)
            assertEquals(stubResult, result.getOrNull())
        }

    @Test
    fun `does not track stats event when API throws`() =
        runTest {
            coEvery { api.getProperties() } throws RuntimeException("error")
            every { throwableToInternetConnectionError.map(any()) } returns InternetConnectionError.Unknown

            repository.getProperties()

            verify(exactly = 0) { statsRepository.trackEvent(any(), any()) }
        }

    @Test
    fun `getPropertyById returns the property matching the given id`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            val result = repository.getPropertyById(1)

            assertTrue(result.isSuccess)
            assertEquals(stubProperty, result.getOrNull())
        }

    @Test
    fun `getPropertyById returns null when no property matches`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            val result = repository.getPropertyById(99)

            assertTrue(result.isSuccess)
            org.junit.Assert.assertNull(result.getOrNull())
        }

    @Test
    fun `getPropertyById returns failure when getProperties fails`() =
        runTest {
            val exception = RuntimeException("Network error")
            coEvery { api.getProperties() } throws exception
            every { throwableToInternetConnectionError.map(exception) } returns InternetConnectionError.NoConnection

            val result = repository.getPropertyById(1)

            assertTrue(result.isFailure)
            val failure = result.exceptionOrNull()
            assertTrue(failure is InternetConnectionException)
            assertEquals(InternetConnectionError.NoConnection, (failure as InternetConnectionException).error)
        }

    @Test
    fun `getPropertyById tracks LOAD_DETAILS stats event`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            repository.getPropertyById(1)

            verify { statsRepository.trackEvent(StatsEvent.LOAD_DETAILS, any()) }
        }

    @Test
    fun `getPropertyById returns cached entity without calling the API`() =
        runTest {
            coEvery { propertyDao.getById(1) } returns stubPropertyEntity
            every { propertyEntityToDomain.map(stubPropertyEntity) } returns stubProperty

            val result = repository.getPropertyById(1)

            assertTrue(result.isSuccess)
            assertEquals(stubProperty, result.getOrNull())
            coVerify(exactly = 0) { api.getProperties() }
        }

    @Test
    fun `getPropertyById returns failure instead of throwing when cached entity mapping fails`() =
        runTest {
            val exception = RuntimeException("Corrupted cache row")
            coEvery { propertyDao.getById(1) } returns stubPropertyEntity
            every { propertyEntityToDomain.map(stubPropertyEntity) } throws exception
            every { throwableToInternetConnectionError.map(exception) } returns InternetConnectionError.Unknown

            val result = repository.getPropertyById(1)

            assertTrue(result.isFailure)
            val failure = result.exceptionOrNull()
            assertTrue(failure is InternetConnectionException)
            assertEquals(InternetConnectionError.Unknown, (failure as InternetConnectionException).error)
            assertEquals(exception, failure.cause)
        }

    @Test
    fun `getPropertyById does not track stats when served from cache`() =
        runTest {
            coEvery { propertyDao.getById(1) } returns stubPropertyEntity
            every { propertyEntityToDomain.map(stubPropertyEntity) } returns stubProperty

            repository.getPropertyById(1)

            verify(exactly = 0) { statsRepository.trackEvent(any(), any()) }
        }

    @Test
    fun `persist stamps lastUpdatedAt on cached properties`() =
        runTest {
            coEvery { api.getProperties() } returns stubResponse
            every { mapper.map(stubResponse) } returns stubResult

            repository.getProperties()

            coVerify { propertyDao.insertAll(match { it.all { entity -> entity.lastUpdatedAt > 0L } }) }
        }
}
