package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.NetworkStatusRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ObserveNetworkStatusUseCaseImplTest {
    private val repository = mockk<NetworkStatusRepository>()

    private val useCase = ObserveNetworkStatusUseCaseImpl(repository = repository)

    @Test
    fun `returns the flow from the repository`() =
        runTest {
            val flow = MutableStateFlow(true)
            every { repository.isOnline } returns flow

            val result = useCase()

            assertSame(flow, result)
        }

    @Test
    fun `emits true when repository reports online`() =
        runTest {
            every { repository.isOnline } returns flowOf(true)

            val emission = useCase().first()

            assertEquals(true, emission)
        }

    @Test
    fun `emits false when repository reports offline`() =
        runTest {
            every { repository.isOnline } returns flowOf(false)

            val emission = useCase().first()

            assertEquals(false, emission)
        }
}
