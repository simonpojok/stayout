package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDataModel(
    val city: CityDataModel,
)
