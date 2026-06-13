package com.example.stayout.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class FacilityCategoryUiModel(
    val name: String,
    val facilities: List<String>,
)
