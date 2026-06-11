package com.example.stayout.data.repository

import com.example.stayout.data.local.dao.CommentDao
import com.example.stayout.data.local.dao.UserDao
import com.example.stayout.data.local.entity.CommentEntity
import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.data.local.mapper.CommentEntityToDomainMapper
import com.example.stayout.data.mapper.CommentToDomainMapper
import com.example.stayout.data.mapper.UserDataToEntityMapper
import com.example.stayout.data.remote.api.CommentApi
import com.example.stayout.data.remote.model.CommentDataModel
import com.example.stayout.data.remote.model.UserDataModel
import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.domain.model.UserDomainModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class CommentRepositoryImplTest {
    private val api: CommentApi = mockk()
    private val mapper: CommentToDomainMapper = mockk()
    private val commentDao: CommentDao = mockk(relaxed = true)
    private val userDao: UserDao = mockk(relaxed = true)
    private val userDataToEntity: UserDataToEntityMapper = mockk(relaxed = true)
    private val commentEntityToDomain: CommentEntityToDomainMapper = mockk()
    private val scope = TestScope(UnconfinedTestDispatcher())
    private val repository =
        CommentRepositoryImpl(
            api,
            mapper,
            commentDao,
            userDao,
            userDataToEntity,
            commentEntityToDomain,
            scope,
        )

    private val postId = 1
    private val commentDto =
        CommentDataModel(id = 1, postId = postId, name = "commenter", email = "c@c.com", body = "Great!")
    private val userDto: UserDataModel = mockk()
    private val userDomain: UserDomainModel = mockk()
    private val domainComment = CommentDomainModel(id = 1, postId = postId, user = userDomain, body = "Great!")

    @Test
    fun `matches comment to user from users API`() {
        every { api.getComments(postId) } returns Single.just(listOf(commentDto))
        every { api.getUsers() } returns Single.just(listOf(userDto))
        every { mapper.map(commentDto, userDto) } returns domainComment

        val result = repository.getComments(postId).blockingGet()

        assertEquals(listOf(domainComment), result)
    }

    @Test
    fun `returns cached comments when network fails`() {
        val commentEntity: CommentEntity = mockk { every { userId } returns 1 }
        val userEntity: UserEntity = mockk { every { id } returns 1 }
        every { api.getComments(postId) } returns Single.error(RuntimeException("net"))
        every { api.getUsers() } returns Single.error(RuntimeException("net"))
        coEvery { commentDao.getByPostId(postId) } returns listOf(commentEntity)
        coEvery { userDao.getAll() } returns listOf(userEntity)
        every { commentEntityToDomain.map(commentEntity, userEntity) } returns domainComment

        val result = repository.getComments(postId).blockingGet()

        assertEquals(listOf(domainComment), result)
    }

    @Test
    fun `returns empty list from cache when network fails and cache is empty`() {
        every { api.getComments(postId) } returns Single.error(RuntimeException("net"))
        every { api.getUsers() } returns Single.error(RuntimeException("net"))
        coEvery { commentDao.getByPostId(postId) } returns emptyList()
        coEvery { userDao.getAll() } returns emptyList()

        val result = repository.getComments(postId).blockingGet()

        assertEquals(emptyList<CommentDomainModel>(), result)
    }

    @Test
    fun `propagates DAO error when cache read throws`() {
        every { api.getComments(postId) } returns Single.error(RuntimeException("net"))
        every { api.getUsers() } returns Single.error(RuntimeException("net"))
        coEvery { commentDao.getByPostId(postId) } throws RuntimeException("dao")

        repository
            .getComments(postId)
            .test()
            .awaitDone(2, TimeUnit.SECONDS)
            .assertError(RuntimeException::class.java)
    }

    @Test
    fun `skips comment when matching user is not found in cache`() {
        val commentEntity: CommentEntity = mockk { every { userId } returns 99 }
        val userEntity: UserEntity = mockk { every { id } returns 1 }
        every { api.getComments(postId) } returns Single.error(RuntimeException("net"))
        every { api.getUsers() } returns Single.error(RuntimeException("net"))
        coEvery { commentDao.getByPostId(postId) } returns listOf(commentEntity)
        coEvery { userDao.getAll() } returns listOf(userEntity)

        val result = repository.getComments(postId).blockingGet()

        assertEquals(emptyList<CommentDomainModel>(), result)
    }

    @Test
    fun `cycles through users when comment count exceeds user count`() {
        val comments =
            (1..3).map { i ->
                CommentDataModel(id = i, postId = postId, name = "n", email = "e", body = "b")
            }
        val domain = comments.map { CommentDomainModel(id = it.id, postId = postId, user = userDomain, body = it.body) }
        every { api.getComments(postId) } returns Single.just(comments)
        every { api.getUsers() } returns Single.just(listOf(userDto))
        comments.forEachIndexed { i, c -> every { mapper.map(c, userDto) } returns domain[i] }

        val result = repository.getComments(postId).blockingGet()

        assertTrue(result.all { it.user === userDomain })
    }
}
