package com.example.stayout.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsApi {
    @GET("pedrotrabulo-hw/6bed011203c6c8217f0d55f74ddcc5c5/raw/ce8f55cfd963aeef70f2ac9f88f34cefd19fca30/stats")
    suspend fun trackEvent(
        @Query("action") action: String,
        @Query("duration") duration: Long,
    ): ResponseBody
}
