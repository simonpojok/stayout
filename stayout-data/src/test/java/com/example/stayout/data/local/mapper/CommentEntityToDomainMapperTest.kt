package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.CommentEntity
import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.domain.model.UserDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentEntityToDomainMapperTest {
    private val userEntityToDomain: UserEntityToDomainMapper = mockk()
    private val mapper = CommentEntityToDomainMapper(userEntityToDomain)

    private val userEntity: UserEntity = mockk()
    private val userDomain: UserDomainModel = mockk()
    private val commentEntity = CommentEntity(id = 5, postId = 2, body = "Lovely place!", userId = 3)

    @Test
    fun `map converts comment and user entities to domain model`() {
        every { userEntityToDomain.map(userEntity) } returns userDomain

        val domain = mapper.map(commentEntity, userEntity)

        assertEquals(5, domain.id)
        assertEquals(2, domain.postId)
        assertEquals("Lovely place!", domain.body)
        assertEquals(userDomain, domain.user)
    }
}
