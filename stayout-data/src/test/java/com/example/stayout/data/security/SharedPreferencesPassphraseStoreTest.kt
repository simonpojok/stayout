package com.example.stayout.data.security

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Base64

class SharedPreferencesPassphraseStoreTest {
    private val mockEditor: SharedPreferences.Editor = mockk(relaxed = true)
    private val mockPrefs: SharedPreferences = mockk()
    private val mockContext: Context = mockk()

    private lateinit var store: SharedPreferencesPassphraseStore

    @Before
    fun setUp() {
        every { mockContext.getSharedPreferences(any(), any()) } returns mockPrefs
        every { mockPrefs.edit() } returns mockEditor
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.apply() } just runs
        store = SharedPreferencesPassphraseStore(mockContext)
    }

    @Test
    fun `hasPassphrase returns false when key is not stored`() {
        every { mockPrefs.contains("enc_pass") } returns false

        assertFalse(store.hasPassphrase())
    }

    @Test
    fun `hasPassphrase returns true when key is stored`() {
        every { mockPrefs.contains("enc_pass") } returns true

        assertTrue(store.hasPassphrase())
    }

    @Test
    fun `savePassphrase stores base64-encoded encrypted bytes`() {
        val encrypted = ByteArray(48) { it.toByte() }
        val iv = ByteArray(12) { it.toByte() }
        val expectedEncoded = Base64.getEncoder().encodeToString(encrypted)

        store.savePassphrase(encrypted, iv)

        verify { mockEditor.putString("enc_pass", expectedEncoded) }
    }

    @Test
    fun `savePassphrase stores base64-encoded iv`() {
        val encrypted = ByteArray(48) { it.toByte() }
        val iv = ByteArray(12) { it.toByte() }
        val expectedIvEncoded = Base64.getEncoder().encodeToString(iv)

        store.savePassphrase(encrypted, iv)

        verify { mockEditor.putString("enc_iv", expectedIvEncoded) }
    }

    @Test
    fun `savePassphrase commits via apply`() {
        store.savePassphrase(ByteArray(48), ByteArray(12))

        verify { mockEditor.apply() }
    }

    @Test
    fun `loadPassphrase decodes and returns encrypted bytes`() {
        val encrypted = ByteArray(48) { it.toByte() }
        val iv = ByteArray(12) { it.toByte() }
        every { mockPrefs.getString("enc_pass", "") } returns Base64.getMimeEncoder().encodeToString(encrypted)
        every { mockPrefs.getString("enc_iv", "") } returns Base64.getMimeEncoder().encodeToString(iv)

        val (resultEncrypted, _) = store.loadPassphrase()

        assertArrayEquals(encrypted, resultEncrypted)
    }

    @Test
    fun `loadPassphrase decodes and returns iv`() {
        val encrypted = ByteArray(48) { it.toByte() }
        val iv = ByteArray(12) { it.toByte() }
        every { mockPrefs.getString("enc_pass", "") } returns Base64.getMimeEncoder().encodeToString(encrypted)
        every { mockPrefs.getString("enc_iv", "") } returns Base64.getMimeEncoder().encodeToString(iv)

        val (_, resultIv) = store.loadPassphrase()

        assertArrayEquals(iv, resultIv)
    }

    @Test
    fun `loadPassphrase tolerates legacy data encoded with newlines`() {
        val encrypted = ByteArray(48) { it.toByte() }
        val iv = ByteArray(12) { it.toByte() }
        // Simulate android.util.Base64.DEFAULT output which wraps lines with \n
        every { mockPrefs.getString("enc_pass", "") } returns Base64.getMimeEncoder().encodeToString(encrypted)
        every { mockPrefs.getString("enc_iv", "") } returns Base64.getMimeEncoder().encodeToString(iv)

        val (resultEncrypted, resultIv) = store.loadPassphrase()

        assertArrayEquals(encrypted, resultEncrypted)
        assertArrayEquals(iv, resultIv)
    }

    @Test
    fun `savePassphrase and loadPassphrase round-trip produces original bytes`() {
        val encrypted = ByteArray(48) { (it * 3).toByte() }
        val iv = ByteArray(12) { (it * 7).toByte() }
        val stored = mutableMapOf<String, String>()

        every { mockEditor.putString(any(), any()) } answers {
            stored[firstArg()] = secondArg()
            mockEditor
        }
        every { mockPrefs.getString("enc_pass", "") } answers { stored["enc_pass"] ?: "" }
        every { mockPrefs.getString("enc_iv", "") } answers { stored["enc_iv"] ?: "" }

        store.savePassphrase(encrypted, iv)
        val (resultEncrypted, resultIv) = store.loadPassphrase()

        assertArrayEquals(encrypted, resultEncrypted)
        assertArrayEquals(iv, resultIv)
    }
}
