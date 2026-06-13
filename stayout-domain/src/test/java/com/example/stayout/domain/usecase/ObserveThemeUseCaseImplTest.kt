package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.ThemeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

class ObserveThemeUseCaseImplTest {
    private val repository = mockk<ThemeRepository>()
    private val useCase = ObserveThemeUseCaseImpl(repository)

    @Test
    fun `returns the flow from the repository`() =
        runTest {
            val flow = MutableStateFlow<Boolean?>(null)
            every { repository.isDarkTheme } returns flow

            val result = useCase()

            assertSame(flow, result)
        }

    @Test
    fun `emits null when no preference stored`() =
        runTest {
            every { repository.isDarkTheme } returns flowOf(null)

            val result = useCase().first()

            assertNull(result)
        }

    @Test
    fun `emits true when dark theme stored`() =
        runTest {
            every { repository.isDarkTheme } returns flowOf(true)

            val result = useCase().first()

            assertEquals(true, result)
        }

    @Test
    fun `emits false when light theme stored`() =
        runTest {
            every { repository.isDarkTheme } returns flowOf(false)

            val result = useCase().first()

            assertEquals(false, result)
        }
}
