package space.rodionov.selfanalysis.data

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

private const val TAG = "PreferencesRepository"

data class EmoFilterPreferences(val filterOn: Boolean, val emoFilterJson: String)

@Singleton
class PreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_preferences")

    val emotionFlow = dataStore.data
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

    val mainFilterFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val filterOn = preferences[PreferencesKeys.FILTER_ON] ?: false
            val emoFilterJson = preferences[PreferencesKeys.EMO_FILTER_JSON] ?: ""
            EmoFilterPreferences(filterOn, emoFilterJson)
        }

    val emoFilterJsonFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val emoFilterJson = preferences[PreferencesKeys.EMO_FILTER_JSON] ?: ""
            emoFilterJson
        }

    val filteredFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val filtered = preferences[PreferencesKeys.FILTER_ON] ?: false
            filtered
        }

    val modeFlow = dataStore.data
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

    val followSystemModeFlow = dataStore.data
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

    suspend fun updateFilterOn(filterOn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FILTER_ON] = filterOn
            Log.d(TAG, "Filter is ON: $filterOn")
        }
    }

    suspend fun updateEmoFilterList(/*emotion: String, emotionAdded: Boolean, */emoFilterJson: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMO_FILTER_JSON] = emoFilterJson
            Log.d(TAG, "EMO FILTER IS SUCCESSFULLY UPDATED =)")
            Log.d(TAG, "filterJson: $emoFilterJson")
        }
    }

    suspend fun updateEmotion(emotion: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMOTION] = emotion
            Log.d(TAG, "updateEmotion: $emotion")
        }
    }

    suspend fun updateMode(mode: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MODE] = mode
            Log.d(TAG, "New mode: $mode")
        }
    }

    suspend fun updateFollowSystemMode(follow: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FOLLOW_SYSTEM_MODE] = follow
            Log.d(TAG, "NewFollowing system mode: $follow")
        }
    }

    private object PreferencesKeys {
        val EMOTION = preferencesKey<String>("emotion")
        val EMO_FILTER_JSON = preferencesKey<String>("emo_filter_list")
        val FILTER_ON = preferencesKey<Boolean>("filter_on")
        val MODE = preferencesKey<Int>("mode")
        val FOLLOW_SYSTEM_MODE = preferencesKey<Boolean>("follow_system_mode")
    }
}






