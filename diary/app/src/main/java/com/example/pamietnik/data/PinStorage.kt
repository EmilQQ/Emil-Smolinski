package com.example.pamietnik.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PinStorage : IPinStorage{
    private val _Key_PIN = "user_pin"
    private val _PREF_FILE = "secure_prefs"
    override val KEY_PIN: String
        get() = _Key_PIN
    override val PREF_FILE: String
        get() = _PREF_FILE

    override fun getPrefs(context: Context) : EncryptedSharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            _PREF_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    override fun isPinSet(context: Context): Boolean =
        getPrefs(context).contains(_Key_PIN)

    override fun savePin(context: Context, pin: String) {
        getPrefs(context)
            .edit()
            .putString(_Key_PIN, pin)
            .apply()
    }

    override fun checkPin(context: Context, input: String): Boolean {
        val stored = getPrefs(context).getString(_Key_PIN, null)
        return stored != null && stored == input
    }
}