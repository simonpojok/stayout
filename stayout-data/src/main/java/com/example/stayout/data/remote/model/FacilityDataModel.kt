package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FacilityDataModel(
    val name: String,
    val id: String,
)
