package com.example.stayout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stayout.data.local.entity.ExchangeRatesEntity

@Dao
interface ExchangeRatesDao {
    @Query("SELECT * FROM exchange_rates WHERE id = 1")
    suspend fun get(): ExchangeRatesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rates: ExchangeRatesEntity)
}
