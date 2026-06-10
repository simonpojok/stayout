package com.example.stayout.domain.repository

interface StatsRepository {
    fun trackEvent(
        action: String,
        duration: Long,
    )
}
