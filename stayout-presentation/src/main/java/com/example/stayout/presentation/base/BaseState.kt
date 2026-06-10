package com.example.stayout.presentation.base

/**
 * Marker interface for all UI states in the MVI pattern.
 *
 * Each screen's state should implement this interface and be a data class
 * or sealed interface representing every possible visual configuration of that screen.
 *
 * States must be immutable — always produce a new instance via copy() rather
 * than mutating fields in place.
 */
interface BaseState
