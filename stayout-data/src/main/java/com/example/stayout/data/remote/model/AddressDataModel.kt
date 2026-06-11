package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressDataModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoDataModel,
)
