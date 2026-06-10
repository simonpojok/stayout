package com.example.stayout.data.security

import java.security.SecureRandom

internal class DatabasePassphraseRepositoryImpl(
    private val store: PassphraseStore,
    private val cipher: PassphraseCipher,
) : DatabasePassphraseRepository {
    override fun getOrCreatePassphrase(): ByteArray {
        if (!store.hasPassphrase()) {
            val raw = ByteArray(32).also { SecureRandom().nextBytes(it) }
            val (encrypted, iv) = cipher.encrypt(raw)
            store.savePassphrase(encrypted, iv)
            return raw
        }
        val (encrypted, iv) = store.loadPassphrase()
        return cipher.decrypt(encrypted, iv)
    }
}
