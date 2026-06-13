package com.example.stayscout.debug

import android.content.Context
import android.content.Intent
import com.airbnb.android.showkase.models.Showkase

object ShowkaseLauncher {
    fun getBrowserIntent(context: Context): Intent =
        Showkase
            .getBrowserIntent(context)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
}
