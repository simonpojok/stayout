package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceDataModel(
    val value: String,
    val currency: String,
)
