package com.example.stayscout.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object List : Screen()

    @Serializable
    data class Detail(
        val propertyId: Int,
    ) : Screen()
}
