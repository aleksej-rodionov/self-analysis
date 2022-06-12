package space.rodionov.selfanalysis.feature_self_analysis.domain.manager

import kotlinx.coroutines.flow.Flow
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo

class PrefManager(
    private val prefRepo: PrefRepo
) {

    fun emotionFlow(): Flow<String> {
        return prefRepo.emotionFlow()
    }

    fun modeFLow(): Flow<Int> {
        return prefRepo.modeFlow()
    }

    fun followSystemMode(): Flow<Boolean> {
        return prefRepo.followSystemMode()
    }

    suspend fun updateEmotion(emotion: String) {
        prefRepo.updateEmotion(emotion)
    }

    suspend fun updateMode(mode: Int) {
        prefRepo.updateMode(mode)
    }

    suspend fun updateFollowSystemMode(follow: Boolean) {
        prefRepo.updateFollowSystemMode(follow)
    }

}