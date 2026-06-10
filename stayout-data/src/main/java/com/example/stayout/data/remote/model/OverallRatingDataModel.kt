package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class OverallRatingDataModel(
    val overall: Int,
    val numberOfRatings: String = "0",
)
