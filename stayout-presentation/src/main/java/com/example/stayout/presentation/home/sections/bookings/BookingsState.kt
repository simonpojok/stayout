package com.example.stayout.presentation.home.sections.bookings

import com.example.stayout.presentation.base.BaseState

sealed interface BookingsState : BaseState {
    data object Idle : BookingsState
}
