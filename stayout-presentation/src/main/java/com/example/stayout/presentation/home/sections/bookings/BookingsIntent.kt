package com.example.stayout.presentation.home.sections.bookings

import com.example.stayout.presentation.base.BaseIntent

sealed interface BookingsIntent : BaseIntent {
    data object None : BookingsIntent
}
