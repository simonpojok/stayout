package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoDataModel(
    val lat: String,
    val lng: String,
)
