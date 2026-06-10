package com.example.stayout.presentation.provider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(
        @StringRes resId: Int,
        vararg args: Any,
    ): String
}
