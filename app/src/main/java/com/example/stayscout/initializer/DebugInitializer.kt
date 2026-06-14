package com.example.stayscout.initializer

import android.app.Application

object DebugInitializer {
    fun install(app: Application) {
        com.example.stayscout.debug.DebugActivityFixer
            .install(app)
        timber.log.Timber.plant(timber.log.Timber.DebugTree())
        android.os.StrictMode.setThreadPolicy(
            android.os.StrictMode.ThreadPolicy
                .Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )
        android.os.StrictMode.setVmPolicy(
            android.os.StrictMode.VmPolicy
                .Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )
    }
}
