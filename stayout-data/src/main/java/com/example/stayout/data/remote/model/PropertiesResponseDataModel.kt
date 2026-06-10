package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesResponseDataModel(
    val properties: List<PropertyDataModel>,
    val location: LocationDataModel,
)
