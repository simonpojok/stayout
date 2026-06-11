package com.example.stayout.data.local.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class CommentEntityTest {
    private val entity = CommentEntity(id = 1, postId = 10, body = "Great stay!", userId = 42)

    @Test
    fun `holds all comment fields`() {
        assertEquals(1, entity.id)
        assertEquals(10, entity.postId)
        assertEquals("Great stay!", entity.body)
        assertEquals(42, entity.userId)
    }

    @Test
    fun `copy produces updated entity`() {
        val copy = entity.copy(body = "Terrible stay!")
        assertEquals("Terrible stay!", copy.body)
        assertEquals(entity.id, copy.id)
    }

    @Test
    fun `equals and hashCode are value-based`() {
        val other = entity.copy()
        assertEquals(entity, other)
        assertEquals(entity.hashCode(), other.hashCode())
    }
}
