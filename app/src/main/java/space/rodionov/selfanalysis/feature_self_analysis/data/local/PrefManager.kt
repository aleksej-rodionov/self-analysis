package space.rodionov.selfanalysis.feature_self_analysis.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PrefManager"

@Singleton
class PrefManager @Inject constructor(@ApplicationContext context: Context)  {
    private val prefStore = context.createDataStore("user_preferences")

    val emotionFlow = prefStore.data
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

    val modeFlow = prefStore.data
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

    val followSystemModeFlow = prefStore.data
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

    suspend fun updateEmotion(emotion: String) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.EMOTION] = emotion
            Log.d(TAG, "updateEmotion: $emotion")
        }
    }

    suspend fun updateMode(mode: Int) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.MODE] = mode
            Log.d(TAG, "New mode: $mode")
        }
    }

    suspend fun updateFollowSystemMode(follow: Boolean) {
        prefStore.edit { preferences ->
            preferences[PreferencesKeys.FOLLOW_SYSTEM_MODE] = follow
            Log.d(TAG, "NewFollowing system mode: $follow")
        }
    }

    private object PreferencesKeys {
        val EMOTION = preferencesKey<String>("emotion")
        val MODE = preferencesKey<Int>("mode")
        val FOLLOW_SYSTEM_MODE = preferencesKey<Boolean>("follow_system_mode")
    }
}