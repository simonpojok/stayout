package com.example.stayout.domain.model

sealed class AnalyticsEvent {
    abstract fun toLogString(): String

    data class ScreenViewed(
        val screen: String,
    ) : AnalyticsEvent() {
        override fun toLogString() = "screen_view: screen=$screen"
    }

    data class PropertyTapped(
        val propertyId: Int,
    ) : AnalyticsEvent() {
        override fun toLogString() = "property_tap: id=$propertyId"
    }

    data class CurrencyChanged(
        val from: String,
        val to: String,
    ) : AnalyticsEvent() {
        override fun toLogString() = "currency_change: from=$from to=$to"
    }

    data class SearchPerformed(
        val query: String,
        val resultCount: Int,
    ) : AnalyticsEvent() {
        override fun toLogString() = "search: query=$query results=$resultCount"
    }
}
