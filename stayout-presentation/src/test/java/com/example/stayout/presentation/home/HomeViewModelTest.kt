package com.example.stayout.presentation.home

import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.ObserveThemeUseCase
import com.example.stayout.domain.usecase.SetThemeUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private val observeThemeUseCase: ObserveThemeUseCase = mockk()
    private val setThemeUseCase: SetThemeUseCase = mockk()
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coJustRun { setThemeUseCase(any()) }
        coEvery { observeNetworkStatusUseCase() } returns flowOf(true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): HomeViewModel =
        HomeViewModel(observeThemeUseCase, setThemeUseCase, observeNetworkStatusUseCase)

    @Test
    fun `theme flow null transitions to Ready with isDarkTheme false`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(null)

            val vm = createViewModel()
            val state = vm.state.value as HomeState.Ready

            assertFalse(state.isDarkTheme)
        }

    @Test
    fun `theme flow true transitions to Ready with isDarkTheme true`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(true)

            val vm = createViewModel()
            val state = vm.state.value as HomeState.Ready

            assertTrue(state.isDarkTheme)
        }

    @Test
    fun `theme flow false transitions to Ready with isDarkTheme false`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)

            val vm = createViewModel()
            val state = vm.state.value as HomeState.Ready

            assertFalse(state.isDarkTheme)
        }

    @Test
    fun `theme flow emits ThemeChanged event`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(true)

            val vm = createViewModel()
            val event = vm.events.first() as HomeEvent.ThemeChanged

            assertTrue(event.isDarkTheme)
        }

    @Test
    fun `ToggleTheme calls setThemeUseCase with opposite of current isDarkTheme`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(true)

            val vm = createViewModel()
            vm.onIntent(HomeIntent.ToggleTheme)

            coVerify { setThemeUseCase(false) }
        }

    @Test
    fun `ToggleTheme when isDarkTheme false persists true`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)

            val vm = createViewModel()
            vm.onIntent(HomeIntent.ToggleTheme)

            coVerify { setThemeUseCase(true) }
        }

    @Test
    fun `ToggleTheme is ignored when state is Initializing`() =
        runTest(testDispatcher) {
            // emptyFlow completes without emitting, so state never leaves Initializing
            coEvery { observeThemeUseCase() } returns emptyFlow()

            val vm = HomeViewModel(observeThemeUseCase, setThemeUseCase, observeNetworkStatusUseCase)
            vm.onIntent(HomeIntent.ToggleTheme)

            coVerify(exactly = 0) { setThemeUseCase(any()) }
        }

    @Test
    fun `UpdateSearch updates searchQuery in Ready state`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)

            val vm = createViewModel()
            vm.onIntent(HomeIntent.UpdateSearch("Dublin"))

            val state = vm.state.value as HomeState.Ready
            assertEquals("Dublin", state.searchQuery)
        }

    @Test
    fun `network offline sets isOffline true in Ready state`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)
            coEvery { observeNetworkStatusUseCase() } returns flowOf(false)

            val vm = createViewModel()
            val state = vm.state.value as HomeState.Ready

            assertTrue(state.isOffline)
        }

    @Test
    fun `network online sets isOffline false in Ready state`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)
            coEvery { observeNetworkStatusUseCase() } returns flowOf(true)

            val vm = createViewModel()
            val state = vm.state.value as HomeState.Ready

            assertFalse(state.isOffline)
        }

    @Test
    fun `BackOnline event emitted when network transitions from offline to online`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)
            val networkFlow = MutableStateFlow(false)
            coEvery { observeNetworkStatusUseCase() } returns networkFlow

            val vm = createViewModel()
            networkFlow.value = true

            // ThemeChanged is always emitted first; drop it and assert BackOnline follows
            val event = vm.events.drop(1).first()
            assertTrue(event is HomeEvent.BackOnline)
        }

    @Test
    fun `BackOnline event is not emitted when network was never offline`() =
        runTest(testDispatcher) {
            coEvery { observeThemeUseCase() } returns flowOf(false)
            coEvery { observeNetworkStatusUseCase() } returns flowOf(true)

            val vm = createViewModel()

            val themeEvent = vm.events.first()
            assertTrue(themeEvent is HomeEvent.ThemeChanged)
        }
}
