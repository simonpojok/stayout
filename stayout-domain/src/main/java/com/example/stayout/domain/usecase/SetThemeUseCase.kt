package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.ThemeRepository
import javax.inject.Inject

abstract class SetThemeUseCase : BaseNoResultUseCase<Boolean>()

class SetThemeUseCaseImpl
    @Inject
    constructor(
        private val repository: ThemeRepository,
    ) : SetThemeUseCase() {
        override suspend fun execute(params: Boolean) = repository.setDarkTheme(params)
    }
