package com.example.stayout.presentation.home.sections.profile

import com.example.stayout.presentation.base.BaseNoIntentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor() :
    BaseNoIntentViewModel<ProfileState, ProfileEvent>(initialState = ProfileState.Idle)
