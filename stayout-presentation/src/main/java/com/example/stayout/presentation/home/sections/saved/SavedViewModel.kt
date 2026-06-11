package com.example.stayout.presentation.home.sections.saved

import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel
    @Inject
    constructor() :
    BaseViewModel<SavedState, SavedIntent, SavedEvent>(
            initialState = SavedState.Idle,
        ) {
        override fun onIntent(intent: SavedIntent) = Unit
    }
