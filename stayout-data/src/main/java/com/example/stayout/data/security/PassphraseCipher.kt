package com.example.stayout.data.security

internal interface PassphraseCipher {
    fun encrypt(data: ByteArray): Pair<ByteArray, ByteArray>

    fun decrypt(
        ciphertext: ByteArray,
        iv: ByteArray,
    ): ByteArray
}
