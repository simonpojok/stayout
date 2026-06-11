package com.example.stayout.domain.model

data class CommentDomainModel(
    val id: Int,
    val postId: Int,
    val user: UserDomainModel,
    val body: String,
)
