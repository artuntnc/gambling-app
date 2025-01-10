package com.example.gamblingapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Define keys for preferences
private val Context.dataStore by preferencesDataStore(name = "settings")

class LocalDataStoreManager (private val context: Context) {

    // Preference Keys
    private object PreferencesKeys {
        val MUSIC_VOLUME = floatPreferencesKey("music_volume")
        val SOUND_VOLUME = floatPreferencesKey("sound_volume")
        val EMAIL = stringPreferencesKey("email")
        val ARE_NOTIFICATIONS_ON = booleanPreferencesKey("are_notifications_on")
        val IS_ALT_THEME_ON = booleanPreferencesKey("is_alt_theme_on")
    }

    //Music volume actions
    suspend fun saveMusicVolume(volume: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.MUSIC_VOLUME] = volume
        }
    }
    val volumeMusicFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.MUSIC_VOLUME] ?: 0.5f // Default value
        }

    //Sound volume actions
    suspend fun saveSoundVolume(volume: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_VOLUME] = volume
        }
    }
    val volumeSoundFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SOUND_VOLUME] ?: 0.5f // Default value
        }

    //Email actions
    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = email
        }
    }
    val emailFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EMAIL] ?: ""
        }

    //Theme actions
    suspend fun saveIsAltThemeOn(isAltThemeOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ALT_THEME_ON] = isAltThemeOn
        }
    }
    val isAltThemeOnFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_ALT_THEME_ON] ?: false
        }

    //Notifications actions
    suspend fun saveAreNotificationsOn(areNotificationsOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ARE_NOTIFICATIONS_ON] = areNotificationsOn
        }
    }
    val areNotificationsOnFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.ARE_NOTIFICATIONS_ON] ?: true
        }
}