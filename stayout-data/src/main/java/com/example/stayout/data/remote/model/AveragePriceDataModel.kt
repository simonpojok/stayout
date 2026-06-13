package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AveragePricePromotionsDataModel(
    val promotionsIds: List<Int> = emptyList(),
    val totalDiscount: String = "0",
)

@Serializable
data class AveragePriceDataModel(
    val value: String = "0",
    val currency: String = "EUR",
    val promotions: AveragePricePromotionsDataModel? = null,
    val original: String? = null,
)
