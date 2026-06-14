package com.example.stayout.domain.model

sealed interface InternetConnectionError {
    data object NoConnection : InternetConnectionError

    data object ServerUnreachable : InternetConnectionError

    data object NotFound : InternetConnectionError

    data object Unknown : InternetConnectionError
}
