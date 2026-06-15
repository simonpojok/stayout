package com.example.stayout.presentation.util

import android.text.format.DateUtils

fun Long.toRelativeTimeString(): CharSequence =
    DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
