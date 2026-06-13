package com.example.stayout.domain.model

data class FacilityDomainModel(
    val id: String,
    val name: String,
)

data class FacilityCategoryDomainModel(
    val id: String = "",
    val name: String,
    val facilities: List<FacilityDomainModel>,
)
