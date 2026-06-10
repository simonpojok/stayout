package com.example.stayout.data.remote.model

import com.example.stayout.data.serializer.BigDecimalSerializer
import com.example.stayout.data.serializer.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate

@Serializable
data class RatesResponseDataModel(
    val success: Boolean,
    val base: String,
    @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
    val rates: Map<
        String,
        @Serializable(with = BigDecimalSerializer::class)
        BigDecimal,
    >,
)
