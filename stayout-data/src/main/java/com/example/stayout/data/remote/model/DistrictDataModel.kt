package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DistrictDataModel(
    val id: String = "",
    val name: String = "",
)
