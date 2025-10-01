package com.example.pamietnik.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface IPinStorage {
    val PREF_FILE: String
    val KEY_PIN: String

    fun getPrefs(context: Context) : EncryptedSharedPreferences

    fun isPinSet(context: Context): Boolean
    fun savePin(context: Context, pin: String)

    fun checkPin(context: Context, input: String): Boolean
}