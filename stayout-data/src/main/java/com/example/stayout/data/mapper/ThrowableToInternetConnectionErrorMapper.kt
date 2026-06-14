package com.example.stayout.data.mapper

import com.example.stayout.domain.model.InternetConnectionError
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ThrowableToInternetConnectionErrorMapper
    @Inject
    constructor() :
    BaseDataToDomainMapper<Throwable, InternetConnectionError> {
        override fun map(model: Throwable): InternetConnectionError =
            when (model) {
                is UnknownHostException -> InternetConnectionError.NoConnection
                is ConnectException -> InternetConnectionError.NoConnection
                is SocketTimeoutException -> InternetConnectionError.ServerUnreachable
                is HttpException ->
                    when {
                        model.code() == HTTP_NOT_FOUND -> InternetConnectionError.NotFound
                        model.code() in HTTP_SERVER_ERROR_RANGE -> InternetConnectionError.ServerUnreachable
                        else -> InternetConnectionError.Unknown
                    }
                is IOException -> InternetConnectionError.NoConnection
                else -> InternetConnectionError.Unknown
            }

        companion object {
            private const val HTTP_NOT_FOUND = 404
            private val HTTP_SERVER_ERROR_RANGE = 500..599
        }
    }
