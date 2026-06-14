package com.example.stayout.data.repository

import android.content.Context
import android.os.Bundle
import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.repository.AnalyticsRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseAnalyticsRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : AnalyticsRepository {
        private val analytics: FirebaseAnalytics? by lazy {
            if (FirebaseApp.getApps(context).isEmpty()) {
                null
            } else {
                FirebaseAnalytics.getInstance(context)
            }
        }

        override fun track(event: AnalyticsEvent) {
            analytics?.logEvent(event.toFirebaseName(), event.toFirebaseParams())
        }

        private fun AnalyticsEvent.toFirebaseName(): String =
            when (this) {
                is AnalyticsEvent.ScreenViewed -> FirebaseAnalytics.Event.SCREEN_VIEW
                is AnalyticsEvent.PropertyTapped -> "property_tap"
                is AnalyticsEvent.CurrencyChanged -> "currency_change"
                is AnalyticsEvent.SearchPerformed -> FirebaseAnalytics.Event.SEARCH
            }

        private fun AnalyticsEvent.toFirebaseParams(): Bundle =
            Bundle().apply {
                when (this@toFirebaseParams) {
                    is AnalyticsEvent.ScreenViewed -> putString(FirebaseAnalytics.Param.SCREEN_NAME, screen)
                    is AnalyticsEvent.PropertyTapped -> putString("property_id", propertyId.toString())
                    is AnalyticsEvent.CurrencyChanged -> {
                        putString("from_currency", from)
                        putString("to_currency", to)
                    }
                    is AnalyticsEvent.SearchPerformed -> {
                        putString(FirebaseAnalytics.Param.SEARCH_TERM, query)
                        putInt("result_count", resultCount)
                    }
                }
            }
    }
