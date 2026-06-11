package com.example.stayout.presentation.util

data class UgandaLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)

val ugandaHotelLocations =
    listOf(
        UgandaLocation("Kampala", 0.3476, 32.5825),
        UgandaLocation("Entebbe", 0.0512, 32.4637),
        UgandaLocation("Jinja", 0.4244, 33.2041),
        UgandaLocation("Gulu", 2.7756, 32.2998),
        UgandaLocation("Mbarara", -0.6072, 30.6545),
        UgandaLocation("Fort Portal", 0.6710, 30.2750),
        UgandaLocation("Masaka", -0.3333, 31.7333),
        UgandaLocation("Kabale", -1.2497, 29.9938),
        UgandaLocation("Mbale", 1.0755, 34.1752),
        UgandaLocation("Soroti", 1.7148, 33.6109),
    )
