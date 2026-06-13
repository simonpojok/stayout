package com.example.stayout.data.repository

import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.repository.AnalyticsRepository
import timber.log.Timber
import javax.inject.Inject

class LoggingAnalyticsRepository
    @Inject
    constructor() : AnalyticsRepository {
        override fun track(event: AnalyticsEvent) {
            when (event) {
                is AnalyticsEvent.ScreenViewed ->
                    Timber.tag(TAG).d("screen_view: screen=%s", event.screen)
                is AnalyticsEvent.PropertyTapped ->
                    Timber.tag(TAG).d("property_tap: id=%d", event.propertyId)
                is AnalyticsEvent.CurrencyChanged ->
                    Timber.tag(TAG).d("currency_change: from=%s to=%s", event.from, event.to)
                is AnalyticsEvent.SearchPerformed ->
                    Timber.tag(TAG).d("search: query=%s results=%d", event.query, event.resultCount)
            }
        }

        companion object {
            private const val TAG = "Analytics"
        }
    }
