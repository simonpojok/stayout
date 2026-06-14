package com.example.stayout.data.repository

import android.content.Context
import com.example.stayout.domain.model.AnalyticsEvent
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseAnalyticsRepositoryTest {
    private val mockAnalytics: FirebaseAnalytics = mockk(relaxed = true)
    private val mockContext: Context = mockk(relaxed = true)
    private lateinit var repository: FirebaseAnalyticsRepository

    @Before
    fun setUp() {
        mockkStatic(FirebaseApp::class)
        mockkStatic(FirebaseAnalytics::class)
        every { FirebaseApp.getApps(any()) } returns listOf(mockk())
        every { FirebaseAnalytics.getInstance(any()) } returns mockAnalytics
        repository = FirebaseAnalyticsRepository(mockContext)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `track ScreenViewed logs screen_view event`() {
        repository.track(AnalyticsEvent.ScreenViewed(screen = "explore"))

        verify { mockAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, any()) }
    }

    @Test
    fun `track PropertyTapped logs property_tap event`() {
        repository.track(AnalyticsEvent.PropertyTapped(propertyId = 42))

        verify { mockAnalytics.logEvent("property_tap", any()) }
    }

    @Test
    fun `track CurrencyChanged logs currency_change event`() {
        repository.track(AnalyticsEvent.CurrencyChanged(from = "EUR", to = "USD"))

        verify { mockAnalytics.logEvent("currency_change", any()) }
    }

    @Test
    fun `track SearchPerformed logs search event`() {
        repository.track(AnalyticsEvent.SearchPerformed(query = "Dublin", resultCount = 5))

        verify { mockAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, any()) }
    }
}
