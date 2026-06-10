package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.NetworkStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class ObserveNetworkStatusUseCase : BaseNoParamUseCase<Flow<Boolean>>()

class ObserveNetworkStatusUseCaseImpl
    @Inject
    constructor(
        private val repository: NetworkStatusRepository,
    ) : ObserveNetworkStatusUseCase() {
        override suspend fun execute(): Flow<Boolean> = repository.isOnline
    }
