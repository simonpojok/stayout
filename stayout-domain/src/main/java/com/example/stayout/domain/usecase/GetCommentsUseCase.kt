package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.domain.repository.CommentRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

abstract class GetCommentsUseCase {
    abstract operator fun invoke(postId: Int): Single<List<CommentDomainModel>>
}

class GetCommentsUseCaseImpl
    @Inject
    constructor(
        private val repository: CommentRepository,
    ) : GetCommentsUseCase() {
        override operator fun invoke(postId: Int): Single<List<CommentDomainModel>> = repository.getComments(postId)
    }
