package com.example.stayout.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3To4 : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE properties ADD COLUMN imageUrlsJson TEXT NOT NULL DEFAULT '[]'")
        db.execSQL("ALTER TABLE properties ADD COLUMN latitude REAL NOT NULL DEFAULT 0.0")
        db.execSQL("ALTER TABLE properties ADD COLUMN longitude REAL NOT NULL DEFAULT 0.0")
        db.execSQL("ALTER TABLE properties ADD COLUMN ratingBreakdownJson TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN distanceKm REAL")
        db.execSQL("ALTER TABLE properties ADD COLUMN isNew INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE properties ADD COLUMN veryPopular INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE properties ADD COLUMN dormPriceValue TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN privatePriceValue TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN promotionsJson TEXT NOT NULL DEFAULT '[]'")
        db.execSQL("ALTER TABLE properties ADD COLUMN averagePriceValue TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN originalPriceValue TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN totalDiscount TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN district TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN isRecommended INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE properties ADD COLUMN starRating INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE properties ADD COLUMN freeCancellationUntil TEXT")
        db.execSQL("ALTER TABLE properties ADD COLUMN minimumStayDescription TEXT")
    }
}
