package com.example.stayout.domain.model

data class RatingBreakdownDomainModel(
    val security: Double,
    val location: Double,
    val staff: Double,
    val funScore: Double,
    val cleanliness: Double,
    val facilities: Double,
    val value: Double,
    val ratingsCount: Int = 0,
)
