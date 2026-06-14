package com.example.stayout.presentation.home.sections.saved

import com.example.stayout.presentation.base.BaseNoIntentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel
    @Inject
    constructor() :
    BaseNoIntentViewModel<SavedState, SavedEvent>(initialState = SavedState.Idle)
