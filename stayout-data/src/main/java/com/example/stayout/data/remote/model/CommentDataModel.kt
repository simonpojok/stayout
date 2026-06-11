package com.example.stayout.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CommentDataModel(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
)
