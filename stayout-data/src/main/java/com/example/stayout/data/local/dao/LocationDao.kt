package com.example.stayout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stayout.data.local.entity.LocationEntity

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE id = 1")
    suspend fun get(): LocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)
}
