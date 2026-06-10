package com.example.stayout.data.remote.api

import com.example.stayout.data.remote.model.PropertiesResponseDataModel
import retrofit2.http.GET

interface PropertyApi {
    @GET(
        "pedrotrabulo-hw/a1517b9da90dd6877385a65f324ffbc3/raw/3aa9563fbf42234d653af721c470dff41f214da6/properties.json",
    )
    suspend fun getProperties(): PropertiesResponseDataModel
}
