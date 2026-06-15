package com.example.stayscout.initializer

import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException

// Undeliverable IOExceptions (e.g. a second Single.zip source failing offline after the first
// already terminated the stream) crash via the default uncaught handler; log them instead.
object RxJavaErrorHandlerInitializer {
    fun install() {
        RxJavaPlugins.setErrorHandler { throwable ->
            val error = (throwable as? UndeliverableException)?.cause ?: throwable
            if (error is IOException || error is InterruptedException) {
                Timber.w(error, "Undeliverable exception ignored")
                return@setErrorHandler
            }
            Thread.currentThread().let { thread ->
                thread.uncaughtExceptionHandler?.uncaughtException(thread, error)
            }
        }
    }
}
