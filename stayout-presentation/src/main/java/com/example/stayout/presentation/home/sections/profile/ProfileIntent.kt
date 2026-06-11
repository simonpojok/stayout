package com.example.stayout.presentation.home.sections.profile

import com.example.stayout.presentation.base.BaseIntent

sealed interface ProfileIntent : BaseIntent {
    data object None : ProfileIntent
}
