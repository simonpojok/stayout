package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.domain.model.RatingBreakdownDomainModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Serializable
private data class StoredRatingBreakdown(
    val security: Double,
    val location: Double,
    val staff: Double,
    val funScore: Double,
    val cleanliness: Double,
    val facilities: Double,
    val value: Double,
    val ratingsCount: Int = 0,
)

@Serializable
private data class StoredPromotion(
    val type: String,
    val label: String,
    val discount: Int,
)

private val converterJson = Json { ignoreUnknownKeys = true }
private val stringListSerializer = ListSerializer(String.serializer())
private val promotionListSerializer = ListSerializer(StoredPromotion.serializer())

fun encodeImageUrls(imageUrls: List<String>): String = converterJson.encodeToString(stringListSerializer, imageUrls)

fun decodeImageUrls(encoded: String): List<String> = converterJson.decodeFromString(stringListSerializer, encoded)

fun encodeRatingBreakdown(breakdown: RatingBreakdownDomainModel): String =
    converterJson.encodeToString(
        StoredRatingBreakdown.serializer(),
        StoredRatingBreakdown(
            security = breakdown.security,
            location = breakdown.location,
            staff = breakdown.staff,
            funScore = breakdown.funScore,
            cleanliness = breakdown.cleanliness,
            facilities = breakdown.facilities,
            value = breakdown.value,
            ratingsCount = breakdown.ratingsCount,
        ),
    )

fun decodeRatingBreakdown(encoded: String): RatingBreakdownDomainModel {
    val stored = converterJson.decodeFromString(StoredRatingBreakdown.serializer(), encoded)
    return RatingBreakdownDomainModel(
        security = stored.security,
        location = stored.location,
        staff = stored.staff,
        funScore = stored.funScore,
        cleanliness = stored.cleanliness,
        facilities = stored.facilities,
        value = stored.value,
        ratingsCount = stored.ratingsCount,
    )
}

fun encodePromotions(promotions: List<PromotionDomainModel>): String =
    converterJson.encodeToString(
        promotionListSerializer,
        promotions.map { StoredPromotion(type = it.type.name, label = it.label, discount = it.discount) },
    )

fun decodePromotions(encoded: String): List<PromotionDomainModel> =
    converterJson.decodeFromString(promotionListSerializer, encoded).map {
        PromotionDomainModel(type = PromotionType.fromString(it.type), label = it.label, discount = it.discount)
    }
