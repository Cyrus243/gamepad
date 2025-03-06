package com.indelible.gamepad.common

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class AppPreferences(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    suspend fun writeToDataStore(value: String, key: Preferences.Key<String>) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    suspend fun readFromDataStore(key: Preferences.Key<String>): String? {
        return try {
            val preferences = dataStore.data.first()
            preferences[key]
        } catch (e: Exception) {
            Log.e(TAG, "readFromDataStore: $e")
            null
        }
    }

    companion object {
        val IP_ADDRESS = stringPreferencesKey( "ip_address")
        val PORT = stringPreferencesKey("port")
        private const val TAG = "AppPreferences"
    }
}