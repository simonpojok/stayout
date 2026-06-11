package com.example.stayout.presentation.home.sections.bookings

import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel
    @Inject
    constructor() :
    BaseViewModel<BookingsState, BookingsIntent, BookingsEvent>(
            initialState = BookingsState.Idle,
        ) {
        override fun onIntent(intent: BookingsIntent) = Unit
    }
