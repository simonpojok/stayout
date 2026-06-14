package com.example.stayout.domain.repository

import com.example.stayout.domain.model.AnalyticsEvent

interface AnalyticsRepository {
    fun track(event: AnalyticsEvent)
}
