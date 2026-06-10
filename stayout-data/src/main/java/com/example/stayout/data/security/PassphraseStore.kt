package com.example.stayout.data.security

internal interface PassphraseStore {
    fun hasPassphrase(): Boolean

    fun savePassphrase(
        encrypted: ByteArray,
        iv: ByteArray,
    )

    fun loadPassphrase(): Pair<ByteArray, ByteArray>
}
