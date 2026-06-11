package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.CommentEntity
import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.domain.model.CommentDomainModel

class CommentEntityToDomainMapper(
    private val userEntityToDomain: UserEntityToDomainMapper,
) {
    fun map(
        comment: CommentEntity,
        user: UserEntity,
    ): CommentDomainModel =
        CommentDomainModel(
            id = comment.id,
            postId = comment.postId,
            body = comment.body,
            user = userEntityToDomain.map(user),
        )
}
