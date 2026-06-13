package com.example.stayout.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class LocationUiModel(
    val cityName: String,
    val countryName: String,
    val displayText: String,
)
