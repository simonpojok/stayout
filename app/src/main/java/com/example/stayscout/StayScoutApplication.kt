package com.example.stayscout

import android.app.Application
import android.os.StrictMode
import com.example.stayscout.debug.DebugActivityFixer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StayScoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DebugActivityFixer.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy
                    .Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy
                    .Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
        }
        org.osmdroid.config.Configuration.getInstance().apply {
            load(this@StayScoutApplication, getSharedPreferences("osmdroid", MODE_PRIVATE))
            userAgentValue = packageName
        }
    }
}
