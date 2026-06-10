package com.example.stayout.data.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
class BigDecimalSerializerTest {
    @Serializable
    private data class Wrapper(
        @Serializable(with = BigDecimalSerializer::class) val value: BigDecimal,
    )

    @Test
    fun `serialize writes unquoted numeric literal via JsonEncoder`() {
        val json = Json.encodeToString(Wrapper.serializer(), Wrapper(BigDecimal("1.234567")))
        assertEquals("""{"value":1.234567}""", json)
    }

    @Test
    fun `serialize uses encodeString via non-Json encoder`() {
        val encoder = TestStringEncoder()
        BigDecimalSerializer.serialize(encoder, BigDecimal("9.99"))
        assertEquals("9.99", encoder.result)
    }

    @Test
    fun `deserialize parses numeric JSON literal via JsonDecoder`() {
        val wrapper = Json.decodeFromString(Wrapper.serializer(), """{"value":42.5}""")
        assertEquals(BigDecimal("42.5"), wrapper.value)
    }

    @Test
    fun `deserialize parses via non-Json decoder using decodeString`() {
        val decoder = TestStringDecoder("3.14")
        val result = BigDecimalSerializer.deserialize(decoder)
        assertEquals(BigDecimal("3.14"), result)
    }

    @Test
    fun `descriptor has correct serial name`() {
        assertEquals("BigDecimal", BigDecimalSerializer.descriptor.serialName)
    }

    private class TestStringEncoder : AbstractEncoder() {
        var result = ""
        override val serializersModule = EmptySerializersModule()

        override fun encodeString(value: String) {
            result = value
        }
    }

    private class TestStringDecoder(
        private val value: String,
    ) : AbstractDecoder() {
        override val serializersModule = EmptySerializersModule()

        override fun decodeString(): String = value

        override fun decodeElementIndex(descriptor: SerialDescriptor): Int = CompositeDecoder.DECODE_DONE
    }
}
