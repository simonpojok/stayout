package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

abstract class GetPropertiesUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseNoParamUseCase<Result<Pair<LocationDomainModel, List<PropertyDomainModel>>>>(dispatcher)

class GetPropertiesUseCaseImpl
    @Inject
    constructor(
        private val repository: PropertyRepository,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : GetPropertiesUseCase(dispatcher) {
        override suspend fun execute(): Result<Pair<LocationDomainModel, List<PropertyDomainModel>>> =
            repository.getProperties()
    }
