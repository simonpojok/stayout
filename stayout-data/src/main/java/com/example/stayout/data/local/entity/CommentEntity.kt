package com.example.stayout.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: Int,
    val postId: Int,
    val body: String,
    val userId: Int,
)
