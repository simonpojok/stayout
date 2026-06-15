package com.example.stayscout

import android.app.Application
import com.example.stayscout.initializer.CrashlyticsInitializer
import com.example.stayscout.initializer.DebugInitializer
import com.example.stayscout.initializer.OsmDroidInitializer
import com.example.stayscout.initializer.RxJavaErrorHandlerInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StayScoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) DebugInitializer.install(this)
        OsmDroidInitializer.install(this)
        RxJavaErrorHandlerInitializer.install()
        if (!BuildConfig.DEBUG) CrashlyticsInitializer.install(this)
    }
}
