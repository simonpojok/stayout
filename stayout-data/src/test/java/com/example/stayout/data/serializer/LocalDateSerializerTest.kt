package com.example.stayout.data.serializer

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class LocalDateSerializerTest {
    @Serializable
    private data class Wrapper(
        @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
    )

    @Test
    fun `serialize encodes LocalDate as ISO-8601 string`() {
        val json = Json.encodeToString(Wrapper.serializer(), Wrapper(LocalDate.of(2024, 6, 15)))
        assertEquals("""{"date":"2024-06-15"}""", json)
    }

    @Test
    fun `deserialize parses ISO-8601 string back to LocalDate`() {
        val wrapper = Json.decodeFromString(Wrapper.serializer(), """{"date":"2023-01-31"}""")
        assertEquals(LocalDate.of(2023, 1, 31), wrapper.date)
    }

    @Test
    fun `round-trip preserves the original date`() {
        val original = LocalDate.of(2025, 12, 25)
        val json = Json.encodeToString(Wrapper.serializer(), Wrapper(original))
        val decoded = Json.decodeFromString(Wrapper.serializer(), json)
        assertEquals(original, decoded.date)
    }

    @Test
    fun `descriptor has correct serial name`() {
        assertEquals("LocalDate", LocalDateSerializer.descriptor.serialName)
    }
}
