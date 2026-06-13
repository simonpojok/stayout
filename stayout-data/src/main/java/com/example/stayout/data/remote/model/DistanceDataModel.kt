package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DistanceDataModel(
    val value: Double = 0.0,
    val units: String = "km",
)
