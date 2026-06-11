package com.example.stayout.presentation.home.sections.saved

import com.example.stayout.presentation.base.BaseState

sealed interface SavedState : BaseState {
    data object Idle : SavedState
}
