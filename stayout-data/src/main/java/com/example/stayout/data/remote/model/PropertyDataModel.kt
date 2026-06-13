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
    val ratingBreakdown: RatingBreakdownDataModel? = null,
    val distance: DistanceDataModel? = null,
    val isNew: Boolean = false,
    val veryPopular: Boolean = false,
    val lowestDormPricePerNight: PriceDataModel? = null,
    val lowestPrivatePricePerNight: PriceDataModel? = null,
    val promotions: List<PromotionDataModel> = emptyList(),
    val lowestAveragePricePerNight: AveragePriceDataModel? = null,
    val district: DistrictDataModel? = null,
    val hostelworldRecommends: Boolean = false,
    val starRating: Int = 0,
    val freeCancellationAvailableUntil: String? = null,
    val stayRuleViolations: List<StayRuleViolationDataModel> = emptyList(),
)
