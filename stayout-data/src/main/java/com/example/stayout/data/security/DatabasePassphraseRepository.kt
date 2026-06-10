package com.example.stayout.data.security

interface DatabasePassphraseRepository {
    fun getOrCreatePassphrase(): ByteArray
}
