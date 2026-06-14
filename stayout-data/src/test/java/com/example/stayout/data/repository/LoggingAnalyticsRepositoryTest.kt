package com.example.stayout.data.repository

import com.example.stayout.domain.model.AnalyticsEvent
import org.junit.Test

class LoggingAnalyticsRepositoryTest {
    private val repository = LoggingAnalyticsRepository()

    @Test
    fun `track ScreenViewed does not throw`() {
        repository.track(AnalyticsEvent.ScreenViewed("explore"))
    }

    @Test
    fun `track PropertyTapped does not throw`() {
        repository.track(AnalyticsEvent.PropertyTapped(propertyId = 42))
    }

    @Test
    fun `track CurrencyChanged does not throw`() {
        repository.track(AnalyticsEvent.CurrencyChanged(from = "EUR", to = "USD"))
    }

    @Test
    fun `track SearchPerformed does not throw`() {
        repository.track(AnalyticsEvent.SearchPerformed(query = "Dublin", resultCount = 10))
    }
}
