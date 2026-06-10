package com.example.stayout.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkStatusRepository {
    val isOnline: Flow<Boolean>
}
