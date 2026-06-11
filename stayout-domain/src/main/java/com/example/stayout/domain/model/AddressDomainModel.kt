package com.example.stayout.domain.model

data class AddressDomainModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val latitude: Double,
    val longitude: Double,
)
