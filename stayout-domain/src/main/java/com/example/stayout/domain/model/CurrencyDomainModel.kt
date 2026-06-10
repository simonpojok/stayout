package com.example.stayout.domain.model

enum class CurrencyDomainModel(
    val symbol: String,
    val displayName: String,
) {
    EUR("€", "EUR"),
    USD("$", "USD"),
    GBP("£", "GBP"),
}
