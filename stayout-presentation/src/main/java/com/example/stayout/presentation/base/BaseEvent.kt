package com.example.stayout.presentation.base

/**
 * Marker interface for all one-shot UI events in the MVI pattern.
 *
 * Events represent side effects that should be consumed exactly once — navigation,
 * showing a snackbar, or triggering a dialog. Unlike state, events are not replayed
 * on recomposition; they are delivered via a Channel and consumed by the UI layer.
 */
interface BaseEvent
