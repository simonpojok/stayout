package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StayRuleViolationDataModel(
    val description: String = "",
)
