package com.example.stayout.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel for the MVI pattern.
 *
 * Manages a single [State] stream, a one-shot [Event] channel, and a single entry
 * point for user [Intent]s. Concrete ViewModels only need to supply an initial state
 * and implement [onIntent]; all infrastructure wiring is handled here.
 *
 * @param State  The UI state type — must implement [BaseState].
 * @param Intent The user intent type — must implement [BaseIntent].
 * @param Event  The one-shot event type — must implement [BaseEvent].
 * @param initialState The state the screen starts with before any data loads.
 */
abstract class BaseViewModel<State : BaseState, Intent : BaseIntent, Event : BaseEvent>(
    initialState: State,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)

    /** Observable stream of UI state. Collect this in the Composable via [collectAsStateWithLifecycle]. */
    val state: StateFlow<State> = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)

    /** One-shot event stream. Collect this with [LaunchedEffect] in the Composable. */
    val events = _events.receiveAsFlow()

    /** The current snapshot of state. Safe to read synchronously inside the ViewModel. */
    protected val currentState: State
        get() = _state.value

    /**
     * Entry point for all user interactions. Implement this to translate each intent
     * into state updates and/or event emissions.
     */
    abstract fun onIntent(intent: Intent)

    /**
     * Applies [transform] to the current state and publishes the result.
     * Always called on the StateFlow's update dispatcher — safe to call from any coroutine.
     */
    protected fun updateState(transform: State.() -> State) {
        _state.update(transform)
    }

    /**
     * Sends a one-shot [event] to the UI layer.
     * The event is buffered and delivered once; it will not be replayed on recomposition.
     */
    protected fun emitEvent(event: Event) {
        viewModelScope.launch { _events.send(event) }
    }
}

/**
 * Base ViewModel for screens that observe state and emit events but accept no user intents.
 *
 * Uses [Nothing] as the Intent type parameter — the bottom type in Kotlin's type system.
 * [onIntent] can never be called because no value of type [Nothing] can be constructed,
 * so subclasses are not required to provide a meaningful implementation.
 *
 * Use this for placeholder or display-only screens (e.g. Saved, Bookings, Profile stubs).
 */
abstract class BaseNoIntentViewModel<State : BaseState, Event : BaseEvent>(
    initialState: State,
) : BaseViewModel<State, Nothing, Event>(initialState) {
    final override fun onIntent(intent: Nothing) = Unit
}
