package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CommentDataModel
import com.example.stayout.data.remote.model.UserDataModel
import com.example.stayout.domain.model.UserDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentToDomainMapperTest {
    private val userMapper: UserToDomainMapper = mockk()
    private val mapper = CommentToDomainMapper(userMapper)

    private val userDto: UserDataModel = mockk()
    private val userDomain: UserDomainModel = mockk()
    private val commentDto =
        CommentDataModel(
            id = 3,
            postId = 1,
            name = "reviewer",
            email = "r@r.com",
            body = "Amazing!",
        )

    @Test
    fun `map converts comment and user data models to domain model`() {
        every { userMapper.map(userDto) } returns userDomain

        val domain = mapper.map(commentDto, userDto)

        assertEquals(3, domain.id)
        assertEquals(1, domain.postId)
        assertEquals("Amazing!", domain.body)
        assertEquals(userDomain, domain.user)
    }
}
