package com.example.stayout.data.remote.api

import com.example.stayout.data.remote.model.RatesResponseDataModel
import retrofit2.http.GET

interface RatesApi {
    @GET("pedrotrabulo-hw/16e87e40ca7b9650aa8e1b936f23e14e/raw/c55eb2ef74f39b05e29b9eb8bb5a28225e328464/rates.json")
    suspend fun getRates(): RatesResponseDataModel
}
