package com.example.stayout.data.repository

import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.repository.AnalyticsRepository
import timber.log.Timber
import javax.inject.Inject

class LoggingAnalyticsRepository
    @Inject
    constructor() : AnalyticsRepository {
        override fun track(event: AnalyticsEvent) {
            Timber.tag(TAG).d(event.toLogString())
        }

        companion object {
            private const val TAG = "Analytics"
        }
    }
