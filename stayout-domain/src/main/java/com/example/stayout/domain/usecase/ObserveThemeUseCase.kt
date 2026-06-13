package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class ObserveThemeUseCase : BaseNoParamUseCase<Flow<Boolean?>>()

class ObserveThemeUseCaseImpl
    @Inject
    constructor(
        private val repository: ThemeRepository,
    ) : ObserveThemeUseCase() {
        override suspend fun execute(): Flow<Boolean?> = repository.isDarkTheme
    }
