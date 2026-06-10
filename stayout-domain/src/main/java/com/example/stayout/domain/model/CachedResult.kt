package com.example.stayout.domain.model

data class CachedResult<out T>(
    val data: T,
    val isFromCache: Boolean = false,
)
