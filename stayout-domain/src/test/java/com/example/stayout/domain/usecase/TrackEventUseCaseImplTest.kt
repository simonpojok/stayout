package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.repository.AnalyticsRepository
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TrackEventUseCaseImplTest {
    private val repository = mockk<AnalyticsRepository>()
    private val useCase = TrackEventUseCaseImpl(repository)

    @Test
    fun `execute delegates ScreenViewed to repository track`() =
        runTest {
            val event = AnalyticsEvent.ScreenViewed("explore")
            justRun { repository.track(event) }

            useCase(event)

            verify { repository.track(event) }
        }

    @Test
    fun `execute delegates PropertyTapped to repository track`() =
        runTest {
            val event = AnalyticsEvent.PropertyTapped(propertyId = 42)
            justRun { repository.track(event) }

            useCase(event)

            verify { repository.track(event) }
        }

    @Test
    fun `execute delegates CurrencyChanged to repository track`() =
        runTest {
            val event = AnalyticsEvent.CurrencyChanged(from = "EUR", to = "USD")
            justRun { repository.track(event) }

            useCase(event)

            verify { repository.track(event) }
        }

    @Test
    fun `execute delegates SearchPerformed to repository track`() =
        runTest {
            val event = AnalyticsEvent.SearchPerformed(query = "Dublin", resultCount = 5)
            justRun { repository.track(event) }

            useCase(event)

            verify { repository.track(event) }
        }
}
