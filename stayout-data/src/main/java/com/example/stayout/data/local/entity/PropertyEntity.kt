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
)
