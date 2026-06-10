package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageGalleryDataModel(
    val prefix: String,
    val suffix: String,
) {
    fun toUrl(): String = "https://$prefix$suffix"
}
