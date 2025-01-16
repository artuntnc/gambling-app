package com.example.gamblingapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
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

@Entity(tableName = "localData")
data class LocalData(
    val musicVolume: Float,
    val soundVolume: Float,
    @PrimaryKey
    val email: String,
    val isAltThemeOn: Boolean,
    val areNotificationsOn: Boolean
)

@Dao
interface LocalDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(localData: LocalData)

    @Update
    suspend fun update(localData: LocalData)

    @Delete
    suspend fun delete(localData: LocalData)

    @Query("SELECT * from localData")
    fun getLocalData(): Flow<LocalData>

    @Query("SELECT EXISTS(SELECT 1 FROM localData)")
    suspend fun doesExist(): Boolean
}

@Database(entities = [LocalData::class], version = 1, exportSchema = false)
abstract class LocalDataDatabase : RoomDatabase() {
    abstract val localDataDao: LocalDataDao
}

class LocalDataRepository(private val db : LocalDataDatabase) {
    suspend fun insertLocalData(localData: LocalData)
    {
        db.localDataDao.insert(localData)
    }

    suspend fun updateLocalData(localData: LocalData)
    {
        db.localDataDao.update(localData)
    }

    suspend fun deleteLocalData(localData: LocalData)
    {
        db.localDataDao.delete(localData)
    }

    fun getLocalDataStream(): Flow<LocalData> = db.localDataDao.getLocalData()

    suspend fun doesExist() = db.localDataDao.doesExist()
}