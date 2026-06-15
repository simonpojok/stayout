package com.example.stayout.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val isFeatured: Boolean,
    val rating: Double,
    val ratingCount: String,
    val lowestPriceValue: String,
    val lowestPriceCurrency: String,
    val overview: String,
    val thumbnailUrl: String?,
    val address: String,
    val type: String,
    val facilitiesJson: String,
    val freeCancellationAvailable: Boolean,
    val imageUrlsJson: String = "[]",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ratingBreakdownJson: String? = null,
    val distanceKm: Double? = null,
    val isNew: Boolean = false,
    val veryPopular: Boolean = false,
    val dormPriceValue: String? = null,
    val privatePriceValue: String? = null,
    val promotionsJson: String = "[]",
    val averagePriceValue: String? = null,
    val originalPriceValue: String? = null,
    val totalDiscount: String? = null,
    val district: String? = null,
    val isRecommended: Boolean = false,
    val starRating: Int = 0,
    val freeCancellationUntil: String? = null,
    val minimumStayDescription: String? = null,
    val lastUpdatedAt: Long = 0L,
)
