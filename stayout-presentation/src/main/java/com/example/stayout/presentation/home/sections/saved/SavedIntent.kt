package com.example.stayout.presentation.home.sections.saved

import com.example.stayout.presentation.base.BaseIntent

sealed interface SavedIntent : BaseIntent {
    data object None : SavedIntent
}
