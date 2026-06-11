package com.example.stayout.data.local.entity

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geoLat: String,
    val geoLng: String,
)
