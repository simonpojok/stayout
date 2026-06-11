package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.domain.model.UserDomainModel
import com.example.stayout.domain.repository.CommentRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class GetCommentsUseCaseImplTest {
    private val repository: CommentRepository = mockk()
    private val useCase = GetCommentsUseCaseImpl(repository)

    private val postId = 1
    private val user: UserDomainModel = mockk()

    private val comments =
        listOf(
            CommentDomainModel(id = 1, postId = postId, user = user, body = "Great stay!"),
        )

    @Test
    fun `returns comments from repository`() {
        every { repository.getComments(postId) } returns Single.just(comments)

        val result = useCase(postId).blockingGet()

        assertEquals(comments, result)
    }

    @Test
    fun `propagates error from repository`() {
        every { repository.getComments(postId) } returns Single.error(RuntimeException("timeout"))

        useCase(postId)
            .test()
            .awaitDone(2, TimeUnit.SECONDS)
            .assertError(RuntimeException::class.java)
    }
}
