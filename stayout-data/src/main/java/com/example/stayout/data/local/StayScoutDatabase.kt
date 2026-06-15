package com.example.stayout.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stayout.data.local.dao.CommentDao
import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.local.dao.UserDao
import com.example.stayout.data.local.entity.CommentEntity
import com.example.stayout.data.local.entity.ExchangeRatesEntity
import com.example.stayout.data.local.entity.LocationEntity
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.data.local.entity.UserEntity

@Database(
    entities = [
        PropertyEntity::class,
        LocationEntity::class,
        ExchangeRatesEntity::class,
        CommentEntity::class,
        UserEntity::class,
    ],
    version = 4,
    exportSchema = false,
)
abstract class StayScoutDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao

    abstract fun locationDao(): LocationDao

    abstract fun exchangeRatesDao(): ExchangeRatesDao

    abstract fun commentDao(): CommentDao

    abstract fun userDao(): UserDao
}
