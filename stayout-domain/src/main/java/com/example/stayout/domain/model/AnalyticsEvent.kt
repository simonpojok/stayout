package com.example.stayout.domain.model

sealed class AnalyticsEvent {
    data class ScreenViewed(
        val screen: String,
    ) : AnalyticsEvent()

    data class PropertyTapped(
        val propertyId: Int,
    ) : AnalyticsEvent()

    data class CurrencyChanged(
        val from: String,
        val to: String,
    ) : AnalyticsEvent()

    data class SearchPerformed(
        val query: String,
        val resultCount: Int,
    ) : AnalyticsEvent()
}
