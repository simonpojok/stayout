package com.example.stayout.domain.repository

import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel

interface PropertyRepository {
    suspend fun getProperties(): Result<Pair<LocationDomainModel, List<PropertyDomainModel>>>
}
