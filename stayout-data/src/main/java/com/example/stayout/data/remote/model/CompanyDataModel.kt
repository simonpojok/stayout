package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyDataModel(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)
