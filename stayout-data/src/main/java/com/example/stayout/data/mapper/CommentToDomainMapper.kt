package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CommentDataModel
import com.example.stayout.data.remote.model.UserDataModel
import com.example.stayout.domain.model.CommentDomainModel

class CommentToDomainMapper(
    private val userMapper: UserToDomainMapper,
) {
    fun map(
        comment: CommentDataModel,
        user: UserDataModel,
    ): CommentDomainModel =
        CommentDomainModel(
            id = comment.id,
            postId = comment.postId,
            user = userMapper.map(user),
            body = comment.body,
        )
}
