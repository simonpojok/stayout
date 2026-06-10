package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class GetPropertyByIdUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseUseCase<Int, PropertyDomainModel?>(dispatcher)

class GetPropertyByIdUseCaseImpl(
    private val repository: PropertyRepository,
    private val statsRepository: StatsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetPropertyByIdUseCase(dispatcher) {
    override suspend fun execute(params: Int): PropertyDomainModel? {
        val start = System.currentTimeMillis()
        val property =
            repository
                .getProperties()
                .getOrNull()
                ?.second
                ?.find { it.id == params }
        statsRepository.trackEvent(StatsEvent.LOAD_DETAILS, System.currentTimeMillis() - start)
        return property
    }
}
