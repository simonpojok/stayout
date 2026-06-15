package com.example.stayout.data.repository

import com.example.stayout.data.di.ApplicationScope
import com.example.stayout.data.local.dao.CommentDao
import com.example.stayout.data.local.dao.UserDao
import com.example.stayout.data.local.entity.CommentEntity
import com.example.stayout.data.local.mapper.CommentEntityToDomainMapper
import com.example.stayout.data.mapper.CommentToDomainMapper
import com.example.stayout.data.mapper.UserDataToEntityMapper
import com.example.stayout.data.remote.api.CommentApi
import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.domain.repository.CommentRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentRepositoryImpl(
    private val api: CommentApi,
    private val mapper: CommentToDomainMapper,
    private val commentDao: CommentDao,
    private val userDao: UserDao,
    private val userDataToEntity: UserDataToEntityMapper,
    private val commentEntityToDomain: CommentEntityToDomainMapper,
    @ApplicationScope private val scope: CoroutineScope,
) : CommentRepository {
    override fun getComments(postId: Int): Single<List<CommentDomainModel>> =
        Single
            .zip(
                api.getComments(postId).subscribeOn(Schedulers.io()),
                api.getUsers().subscribeOn(Schedulers.io()),
            ) { comments, users -> Pair(comments, users) }
            .doOnSuccess { (comments, users) ->
                if (users.isEmpty()) return@doOnSuccess
                scope.launch(Dispatchers.IO) {
                    userDao.insertAll(users.map(userDataToEntity::map))
                    val entities =
                        comments.map { comment ->
                            CommentEntity(
                                id = comment.id,
                                postId = comment.postId,
                                body = comment.body,
                                userId = users[(comment.id - 1) % users.size].id,
                            )
                        }
                    commentDao.deleteByPostId(postId)
                    commentDao.insertAll(entities)
                }
            }.map { (comments, users) ->
                if (users.isEmpty()) throw IllegalStateException("No users returned")
                comments.map { comment ->
                    mapper.map(comment, users[(comment.id - 1) % users.size])
                }
            }.onErrorResumeNext { _ ->
                Single.create { emitter ->
                    scope.launch(Dispatchers.IO) {
                        try {
                            val commentEntities = commentDao.getByPostId(postId)
                            val userEntities = userDao.getAll()
                            val result =
                                commentEntities.mapNotNull { comment ->
                                    val user =
                                        userEntities.find { it.id == comment.userId }
                                            ?: return@mapNotNull null
                                    commentEntityToDomain.map(comment, user)
                                }
                            emitter.onSuccess(result)
                        } catch (e: Exception) {
                            emitter.onError(e)
                        }
                    }
                }
            }
}
