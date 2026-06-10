package com.example.stayout.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int = 1,
    val cityName: String,
    val countryName: String,
)
