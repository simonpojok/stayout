package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertyDataModel(
    val id: Int,
    val name: String,
    val isFeatured: Boolean = false,
    val overallRating: OverallRatingDataModel,
    val lowestPricePerNight: PriceDataModel,
    val overview: String = "",
    val imagesGallery: List<ImageGalleryDataModel> = emptyList(),
    val address1: String = "",
    val address2: String? = null,
    val type: String = "",
    val facilities: List<FacilityCategoryDataModel> = emptyList(),
    val freeCancellationAvailable: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
