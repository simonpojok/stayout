package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.repository.AnalyticsRepository
import javax.inject.Inject

abstract class TrackEventUseCase : BaseNoResultUseCase<AnalyticsEvent>()

class TrackEventUseCaseImpl
    @Inject
    constructor(
        private val repository: AnalyticsRepository,
    ) : TrackEventUseCase() {
        override suspend fun execute(params: AnalyticsEvent) = repository.track(params)
    }
