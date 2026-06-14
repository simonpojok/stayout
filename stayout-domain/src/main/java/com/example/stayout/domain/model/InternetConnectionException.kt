package com.example.stayout.domain.model

class InternetConnectionException(
    val error: InternetConnectionError,
    cause: Throwable? = null,
) : Exception(cause)
