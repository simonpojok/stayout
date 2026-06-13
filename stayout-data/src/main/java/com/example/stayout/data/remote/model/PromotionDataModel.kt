package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PromotionDataModel(
    val id: Int = 0,
    val type: String = "",
    val name: String = "",
    val discount: Int = 0,
    val stack: Boolean = false,
)
