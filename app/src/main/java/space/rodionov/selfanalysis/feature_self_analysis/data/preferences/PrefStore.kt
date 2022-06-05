package space.rodionov.selfanalysis.feature_self_analysis.data.preferences

import android.util.Log
import kotlinx.coroutines.flow.Flow

interface PrefStore {

    fun emotionFlow(): Flow<String>
    fun modeFlow(): Flow<Int>
    fun followSystemModeFlow(): Flow<Boolean>
    suspend fun updateEmotion(emotion: String)
    suspend fun updateMode(mode: Int)
    suspend fun updateFollowSystemMode(follow: Boolean)
}