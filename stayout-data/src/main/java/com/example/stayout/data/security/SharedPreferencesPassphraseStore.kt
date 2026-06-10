package com.example.stayout.data.security

import android.content.Context
import java.util.Base64

internal class SharedPreferencesPassphraseStore(
    context: Context,
) : PassphraseStore {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun hasPassphrase(): Boolean = prefs.contains(KEY_ENCRYPTED)

    override fun savePassphrase(
        encrypted: ByteArray,
        iv: ByteArray,
    ) {
        prefs
            .edit()
            .putString(KEY_ENCRYPTED, Base64.getEncoder().encodeToString(encrypted))
            .putString(KEY_IV, Base64.getEncoder().encodeToString(iv))
            .apply()
    }

    override fun loadPassphrase(): Pair<ByteArray, ByteArray> {
        val encrypted = Base64.getMimeDecoder().decode(prefs.getString(KEY_ENCRYPTED, "")!!)
        val iv = Base64.getMimeDecoder().decode(prefs.getString(KEY_IV, "")!!)
        return encrypted to iv
    }

    private companion object {
        const val PREFS_NAME = "stayscout_db_prefs"
        const val KEY_ENCRYPTED = "enc_pass"
        const val KEY_IV = "enc_iv"
    }
}
