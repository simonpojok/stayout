package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CityDataModel(
    val id: Int,
    val name: String,
    val idCountry: Int,
    val country: String,
)
