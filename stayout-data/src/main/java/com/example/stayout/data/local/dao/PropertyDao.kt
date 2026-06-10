package com.example.stayout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stayout.data.local.entity.PropertyEntity

@Dao
interface PropertyDao {
    @Query("SELECT * FROM properties ORDER BY id ASC")
    suspend fun getAll(): List<PropertyEntity>

    @Query("SELECT * FROM properties WHERE id = :id")
    suspend fun getById(id: Int): PropertyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(properties: List<PropertyEntity>)

    @Query("DELETE FROM properties")
    suspend fun deleteAll()
}
