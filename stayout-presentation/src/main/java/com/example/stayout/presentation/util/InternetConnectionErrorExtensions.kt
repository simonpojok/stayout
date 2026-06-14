package com.example.stayout.presentation.util

import androidx.annotation.StringRes
import com.example.stayout.domain.model.InternetConnectionError
import com.example.stayout.presentation.R

@StringRes
fun InternetConnectionError.toMessageRes(): Int =
    when (this) {
        InternetConnectionError.NoConnection -> R.string.error_no_connection
        InternetConnectionError.ServerUnreachable -> R.string.error_server_unreachable
        InternetConnectionError.NotFound -> R.string.error_not_found
        InternetConnectionError.Unknown -> R.string.error_generic
    }
