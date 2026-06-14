package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class GetPropertyByIdUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseUseCase<Int, Result<PropertyDomainModel?>>(dispatcher)

class GetPropertyByIdUseCaseImpl(
    private val repository: PropertyRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetPropertyByIdUseCase(dispatcher) {
    override suspend fun execute(params: Int): Result<PropertyDomainModel?> = repository.getPropertyById(params)
}
