package com.example.stayout.domain.model

enum class PromotionType {
    MOBILE,
    APPS,
    CUSTOM,
    LOS,
    UNKNOWN,
    ;

    companion object {
        fun fromString(value: String): PromotionType =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
    }
}

data class PromotionDomainModel(
    val type: PromotionType,
    val label: String,
    val discount: Int,
)
