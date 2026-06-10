package com.example.stayout.data.repository

import com.example.stayout.data.remote.api.StatsApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test

class StatsRepositoryImplTest {
    private val api = mockk<StatsApi>()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val repository = StatsRepositoryImpl(api, testScope)

    private fun stubResponseBody(): ResponseBody = mockk<ResponseBody>(relaxed = true)

    @Test
    fun `trackEvent calls statsApi with action and duration`() =
        testScope.runTest {
            coEvery { api.trackEvent(any(), any()) } returns stubResponseBody()

            repository.trackEvent("load", 250L)
            testScheduler.advanceUntilIdle()

            coVerify { api.trackEvent("load", 250L) }
        }

    @Test
    fun `trackEvent swallows exception without propagating`() =
        testScope.runTest {
            coEvery { api.trackEvent(any(), any()) } throws RuntimeException("Network down")

            repository.trackEvent("load", 100L)
            testScheduler.advanceUntilIdle()
            // no exception thrown — test passes
        }

    @Test
    fun `trackEvent fires for different event types`() =
        testScope.runTest {
            coEvery { api.trackEvent(any(), any()) } returns stubResponseBody()

            repository.trackEvent("load", 100L)
            repository.trackEvent("load-rates", 200L)
            repository.trackEvent("load-details", 300L)
            testScheduler.advanceUntilIdle()

            coVerify { api.trackEvent("load", 100L) }
            coVerify { api.trackEvent("load-rates", 200L) }
            coVerify { api.trackEvent("load-details", 300L) }
        }
}
