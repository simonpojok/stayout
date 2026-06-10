package com.example.stayout.presentation.provider

import android.content.Context

class AndroidResourceProvider(
    private val context: Context,
) : ResourceProvider {
    override fun getString(
        resId: Int,
        vararg args: Any,
    ): String = context.getString(resId, *args)
}
