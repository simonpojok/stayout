package com.example.stayscout.initializer

import android.app.Application

object OsmDroidInitializer {
    fun install(app: Application) {
        org.osmdroid.config.Configuration.getInstance().apply {
            load(app, app.getSharedPreferences("osmdroid", Application.MODE_PRIVATE))
            userAgentValue = app.packageName
        }
    }
}
