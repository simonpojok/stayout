package com.example.stayout.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRatesEntity(
    @PrimaryKey val id: Int = 1,
    val usd: String,
    val gbp: String,
)
