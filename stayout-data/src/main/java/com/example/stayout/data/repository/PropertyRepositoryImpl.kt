package com.example.stayout.data.repository

import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.local.mapper.LocationDomainToEntityMapper
import com.example.stayout.data.local.mapper.LocationEntityToDomainMapper
import com.example.stayout.data.local.mapper.PropertyDomainToEntityMapper
import com.example.stayout.data.local.mapper.PropertyEntityToDomainMapper
import com.example.stayout.data.mapper.PropertiesResponseToDomainMapper
import com.example.stayout.data.remote.api.PropertyApi
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository

class PropertyRepositoryImpl(
    private val api: PropertyApi,
    private val mapper: PropertiesResponseToDomainMapper,
    private val statsRepository: StatsRepository,
    private val propertyDao: PropertyDao,
    private val locationDao: LocationDao,
    private val propertyEntityToDomain: PropertyEntityToDomainMapper,
    private val propertyDomainToEntity: PropertyDomainToEntityMapper,
    private val locationEntityToDomain: LocationEntityToDomainMapper,
    private val locationDomainToEntity: LocationDomainToEntityMapper,
) : PropertyRepository {
    override suspend fun getProperties(): Result<Pair<LocationDomainModel, List<PropertyDomainModel>>> {
        val start = System.currentTimeMillis()
        return try {
            val response = api.getProperties()
            statsRepository.trackEvent(StatsEvent.LOAD_PROPERTIES, System.currentTimeMillis() - start)
            val result = mapper.map(response)
            persist(result)
            Result.success(result)
        } catch (e: Exception) {
            loadFromCache() ?: Result.failure(e)
        }
    }

    override suspend fun getPropertyById(id: Int): PropertyDomainModel? {
        val start = System.currentTimeMillis()
        val property = getProperties().getOrNull()?.second?.find { it.id == id }
        statsRepository.trackEvent(StatsEvent.LOAD_DETAILS, System.currentTimeMillis() - start)
        return property
    }

    private suspend fun persist(result: Pair<LocationDomainModel, List<PropertyDomainModel>>) {
        locationDao.insert(locationDomainToEntity.map(result.first))
        propertyDao.deleteAll()
        propertyDao.insertAll(result.second.map { propertyDomainToEntity.map(it) })
    }

    private suspend fun loadFromCache(): Result<Pair<LocationDomainModel, List<PropertyDomainModel>>>? {
        val location = locationDao.get()?.let { locationEntityToDomain.map(it) } ?: return null
        val properties = propertyDao.getAll().map { propertyEntityToDomain.map(it) }
        if (properties.isEmpty()) return null
        return Result.success(location to properties)
    }
}
