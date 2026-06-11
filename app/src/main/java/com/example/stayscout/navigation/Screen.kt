package com.example.stayscout.navigation

sealed class Screen(
    val route: String,
) {
    data object List : Screen("list")

    data object Detail : Screen("detail/{propertyId}") {
        const val ARG_PROPERTY_ID = "propertyId"

        fun createRoute(propertyId: Int) = "detail/$propertyId"
    }
}
