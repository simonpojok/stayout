package com.example.stayscout

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StayScoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        org.osmdroid.config.Configuration.getInstance().apply {
            load(this@StayScoutApplication, getSharedPreferences("osmdroid", MODE_PRIVATE))
            userAgentValue = packageName
        }
    }
}
