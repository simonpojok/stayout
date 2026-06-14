package com.example.stayout.presentation.home.sections.bookings

import com.example.stayout.presentation.base.BaseNoIntentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel
    @Inject
    constructor() :
    BaseNoIntentViewModel<BookingsState, BookingsEvent>(initialState = BookingsState.Idle)
