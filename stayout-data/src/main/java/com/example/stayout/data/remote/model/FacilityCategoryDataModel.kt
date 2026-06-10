package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FacilityCategoryDataModel(
    val name: String,
    val id: String,
    val facilities: List<FacilityDataModel> = emptyList(),
)
