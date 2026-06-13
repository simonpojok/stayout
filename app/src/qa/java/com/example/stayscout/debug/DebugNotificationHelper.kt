package com.example.stayscout.debug

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

object DebugNotificationHelper {
    private const val CHANNEL_ID = "stayscout_debug"
    private const val NOTIFICATION_ID = 1002

    fun show(activity: Activity) {
        val nm = activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0,
            )
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, "Debug Features", NotificationManager.IMPORTANCE_DEFAULT),
            )
        }

        val pendingIntent =
            PendingIntent.getActivity(
                activity,
                0,
                ShowkaseLauncher.getBrowserIntent(activity),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        nm.notify(
            NOTIFICATION_ID,
            NotificationCompat
                .Builder(activity, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("StayScout QA")
                .setContentText("Tap to browse UI components")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build(),
        )
    }
}
