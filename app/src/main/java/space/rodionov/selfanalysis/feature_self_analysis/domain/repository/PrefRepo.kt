package space.rodionov.selfanalysis.feature_self_analysis.domain.repository

import kotlinx.coroutines.flow.Flow

interface PrefRepo {

    fun emotionFlow() : Flow<String>

    fun modeFlow( ):Flow<Int>

    fun followSystemMode(): Flow <Boolean>

    suspend fun updateEmotion(emotion: String)

    suspend fun updateMode(mode: Int)

    suspend fun updateFollowSystemMode(follow: Boolean)

}