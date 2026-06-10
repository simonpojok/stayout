package com.example.stayout.data.security

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabasePassphraseRepositoryImplTest {
    private val store: PassphraseStore = mockk(relaxed = true)
    private val cipher: PassphraseCipher = mockk()
    private val repository = DatabasePassphraseRepositoryImpl(store, cipher)

    @Test
    fun `getOrCreatePassphrase returns 32-byte array on first launch`() {
        every { store.hasPassphrase() } returns false
        every { cipher.encrypt(any()) } returns (ByteArray(48) { 1 } to ByteArray(12) { 2 })

        val result = repository.getOrCreatePassphrase()

        assertEquals(32, result.size)
    }

    @Test
    fun `getOrCreatePassphrase saves encrypted passphrase to store on first launch`() {
        val encrypted = ByteArray(48) { 1 }
        val iv = ByteArray(12) { 2 }
        every { store.hasPassphrase() } returns false
        every { cipher.encrypt(any()) } returns (encrypted to iv)

        repository.getOrCreatePassphrase()

        verify(exactly = 1) { store.savePassphrase(encrypted, iv) }
    }

    @Test
    fun `getOrCreatePassphrase returns decrypted passphrase on subsequent launch`() {
        val expected = ByteArray(32) { 7 }
        val encrypted = ByteArray(48) { 1 }
        val iv = ByteArray(12) { 2 }
        every { store.hasPassphrase() } returns true
        every { store.loadPassphrase() } returns (encrypted to iv)
        every { cipher.decrypt(encrypted, iv) } returns expected

        val result = repository.getOrCreatePassphrase()

        assertArrayEquals(expected, result)
    }

    @Test
    fun `getOrCreatePassphrase does not save to store on subsequent launch`() {
        every { store.hasPassphrase() } returns true
        every { store.loadPassphrase() } returns (ByteArray(48) to ByteArray(12))
        every { cipher.decrypt(any(), any()) } returns ByteArray(32)

        repository.getOrCreatePassphrase()

        verify(exactly = 0) { store.savePassphrase(any(), any()) }
    }

    @Test
    fun `getOrCreatePassphrase does not encrypt on subsequent launch`() {
        every { store.hasPassphrase() } returns true
        every { store.loadPassphrase() } returns (ByteArray(48) to ByteArray(12))
        every { cipher.decrypt(any(), any()) } returns ByteArray(32)

        repository.getOrCreatePassphrase()

        verify(exactly = 0) { cipher.encrypt(any()) }
    }

    @Test
    fun `getOrCreatePassphrase passes correct bytes to cipher on first launch`() {
        val capturedInput = mutableListOf<ByteArray>()
        every { store.hasPassphrase() } returns false
        every { cipher.encrypt(capture(capturedInput)) } returns (ByteArray(48) to ByteArray(12))

        repository.getOrCreatePassphrase()

        assertEquals(32, capturedInput.first().size)
    }

    @Test
    fun `getOrCreatePassphrase passes load result to cipher decrypt on subsequent launch`() {
        val encrypted = ByteArray(48) { 5 }
        val iv = ByteArray(12) { 6 }
        every { store.hasPassphrase() } returns true
        every { store.loadPassphrase() } returns (encrypted to iv)
        every { cipher.decrypt(encrypted, iv) } returns ByteArray(32)

        repository.getOrCreatePassphrase()

        verify(exactly = 1) { cipher.decrypt(encrypted, iv) }
    }
}
