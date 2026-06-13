package com.example.stayout.data.repository

import com.example.stayout.data.di.ApplicationScope
import com.example.stayout.data.remote.api.StatsApi
import com.example.stayout.domain.repository.StatsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class StatsRepositoryImpl(
    private val api: StatsApi,
    @ApplicationScope private val scope: CoroutineScope,
) : StatsRepository {
    override fun trackEvent(
        action: String,
        duration: Long,
    ) {
        scope.launch {
            try {
                api.trackEvent(action, duration).close()
            } catch (e: Exception) {
                Timber.e(e, "Failed to track stats event: action=%s duration=%d", action, duration)
            }
        }
    }
}
