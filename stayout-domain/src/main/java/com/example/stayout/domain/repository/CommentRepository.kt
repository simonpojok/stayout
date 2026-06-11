package com.example.stayout.domain.repository

import com.example.stayout.domain.model.CommentDomainModel
import io.reactivex.rxjava3.core.Single

interface CommentRepository {
    fun getComments(postId: Int): Single<List<CommentDomainModel>>
}
