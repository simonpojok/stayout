package com.example.stayout.presentation.home.sections.profile

import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor() :
    BaseViewModel<ProfileState, ProfileIntent, ProfileEvent>(
            initialState = ProfileState.Idle,
        ) {
        override fun onIntent(intent: ProfileIntent) = Unit
    }
