package com.example.stayscout.initializer

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsInitializer {
    fun install() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
