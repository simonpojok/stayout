package com.example.stayout.presentation.home.sections.profile

import com.example.stayout.presentation.base.BaseState

sealed interface ProfileState : BaseState {
    data object Idle : ProfileState
}
