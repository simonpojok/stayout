package com.example.stayscout.debug

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.core.view.WindowCompat

object DebugActivityFixer {
    private val THIRD_PARTY_ACTIVITIES =
        setOf(
            "com.chuckerteam.chucker.internal.ui.MainActivity",
            "com.chuckerteam.chucker.internal.ui.transaction.TransactionActivity",
            "com.airbnb.android.showkase.ui.ShowkaseBrowserActivity",
        )

    fun install(application: Application) {
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(
                    activity: Activity,
                    savedInstanceState: Bundle?,
                ) {
                    if (activity::class.java.name in THIRD_PARTY_ACTIVITIES) {
                        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
                    }
                }

                override fun onActivityStarted(activity: Activity) = Unit

                override fun onActivityResumed(activity: Activity) = Unit

                override fun onActivityPaused(activity: Activity) = Unit

                override fun onActivityStopped(activity: Activity) = Unit

                override fun onActivitySaveInstanceState(
                    activity: Activity,
                    outState: Bundle,
                ) = Unit

                override fun onActivityDestroyed(activity: Activity) = Unit
            },
        )
    }
}
