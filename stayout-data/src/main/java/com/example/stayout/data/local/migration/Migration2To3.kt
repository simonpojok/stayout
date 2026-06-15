package com.example.stayout.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3 : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE properties ADD COLUMN lastUpdatedAt INTEGER NOT NULL DEFAULT 0")
    }
}
