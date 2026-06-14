package com.example.stayout.data.mapper

import com.example.stayout.domain.model.InternetConnectionError
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ThrowableToInternetConnectionErrorMapperTest {
    private val mapper = ThrowableToInternetConnectionErrorMapper()

    private fun httpException(code: Int) = HttpException(Response.error<Any>(code, "".toResponseBody(null)))

    @Test
    fun `map returns NoConnection for UnknownHostException`() {
        assertEquals(InternetConnectionError.NoConnection, mapper.map(UnknownHostException()))
    }

    @Test
    fun `map returns NoConnection for ConnectException`() {
        assertEquals(InternetConnectionError.NoConnection, mapper.map(ConnectException()))
    }

    @Test
    fun `map returns ServerUnreachable for SocketTimeoutException`() {
        assertEquals(InternetConnectionError.ServerUnreachable, mapper.map(SocketTimeoutException()))
    }

    @Test
    fun `map returns NotFound for HttpException 404`() {
        assertEquals(InternetConnectionError.NotFound, mapper.map(httpException(404)))
    }

    @Test
    fun `map returns ServerUnreachable for HttpException 5xx`() {
        assertEquals(InternetConnectionError.ServerUnreachable, mapper.map(httpException(503)))
    }

    @Test
    fun `map returns Unknown for other HttpException codes`() {
        assertEquals(InternetConnectionError.Unknown, mapper.map(httpException(400)))
    }

    @Test
    fun `map returns NoConnection for generic IOException`() {
        assertEquals(InternetConnectionError.NoConnection, mapper.map(IOException()))
    }

    @Test
    fun `map returns Unknown for other throwables`() {
        assertEquals(InternetConnectionError.Unknown, mapper.map(RuntimeException("boom")))
    }
}
