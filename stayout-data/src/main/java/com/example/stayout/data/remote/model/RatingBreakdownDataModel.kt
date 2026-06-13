package com.example.stayout.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingBreakdownDataModel(
    val security: Int = 0,
    val location: Int = 0,
    val staff: Int = 0,
    @SerialName("fun") val funScore: Int = 0,
    @SerialName("clean") val cleanliness: Int = 0,
    val facilities: Int = 0,
    val value: Int = 0,
    val ratingsCount: Int = 0,
)
