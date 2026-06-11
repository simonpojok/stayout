package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDataModel(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressDataModel,
    val phone: String,
    val website: String,
    val company: CompanyDataModel,
)
