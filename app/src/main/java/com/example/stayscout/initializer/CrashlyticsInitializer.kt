package com.example.stayscout.initializer

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsInitializer {
    fun install(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) return
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
