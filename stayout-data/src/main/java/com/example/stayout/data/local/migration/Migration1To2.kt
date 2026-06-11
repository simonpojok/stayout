package com.example.stayout.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `comments` (
                `id` INTEGER PRIMARY KEY NOT NULL,
                `postId` INTEGER NOT NULL,
                `body` TEXT NOT NULL,
                `userId` INTEGER NOT NULL
            )
            """.trimIndent(),
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `users` (
                `id` INTEGER PRIMARY KEY NOT NULL,
                `name` TEXT NOT NULL,
                `username` TEXT NOT NULL,
                `email` TEXT NOT NULL,
                `phone` TEXT NOT NULL,
                `website` TEXT NOT NULL,
                `address_street` TEXT NOT NULL,
                `address_suite` TEXT NOT NULL,
                `address_city` TEXT NOT NULL,
                `address_zipcode` TEXT NOT NULL,
                `address_geoLat` TEXT NOT NULL,
                `address_geoLng` TEXT NOT NULL,
                `company_name` TEXT NOT NULL,
                `company_catchPhrase` TEXT NOT NULL,
                `company_bs` TEXT NOT NULL
            )
            """.trimIndent(),
        )
    }
}
