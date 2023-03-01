package space.rodionov.selfanalysis.feature_self_analysis.data.preferences

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PrefManager"
private val Context.prefStore by preferencesDataStore("datastore")

class PrefStoreJetPack(
    app: Application
) : PrefStore {
//    private val prefStore = context.createDataStore("user_preferences")
    private val prefStore = app.prefStore

    override fun emotionFlow() = prefStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val emotion = preferences[PreferencesKeys.EMOTION] ?: ""
            emotion
        }

    override fun modeFlow() = prefStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val mode = preferences[PreferencesKeys.MODE] ?: 1
            mode
        }

    override fun followSystemModeFlow() = prefStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val follow = preferences[PreferencesKeys.FOLLOW_SYSTEM_MODE] ?: false
            follow
        }

    override suspend fun updateEmotion(emotion: String) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.EMOTION] = emotion
            Log.d(TAG, "updateEmotion: $emotion")
        }
    }

    override suspend fun updateMode(mode: Int) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.MODE] = mode
            Log.d(TAG, "New mode: $mode")
        }
    }

    override suspend fun updateFollowSystemMode(follow: Boolean) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.FOLLOW_SYSTEM_MODE] = follow
            Log.d(TAG, "NewFollowing system mode: $follow")
        }
    }

    private object PreferencesKeys {
        val EMOTION = stringPreferencesKey("emotion")
        val MODE = intPreferencesKey("mode")
        val FOLLOW_SYSTEM_MODE = booleanPreferencesKey("follow_system_mode")
    }
}