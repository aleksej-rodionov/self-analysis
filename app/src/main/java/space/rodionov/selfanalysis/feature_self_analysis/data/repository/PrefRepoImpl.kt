package space.rodionov.selfanalysis.feature_self_analysis.data.repository

import kotlinx.coroutines.flow.Flow
import space.rodionov.selfanalysis.feature_self_analysis.data.preferences.PrefStore
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo

class PrefRepoImpl(
    private val prefStore: PrefStore
) : PrefRepo {

    override fun emotionFlow(): Flow<String> {
        return prefStore.emotionFlow()
    }

    override fun modeFLow(): Flow<Int> {
        return prefStore.modeFlow()
    }

    override fun followSystemMode(): Flow<Boolean> {
        return prefStore.followSystemModeFlow()
    }

    override suspend fun updateEmotion(emotion: String) {
        prefStore.updateEmotion(emotion)
    }

    override suspend fun updateMode(mode: Int) {
        prefStore.updateMode(mode)
    }

    override suspend fun updateFollowSystemMode(follow: Boolean) {
        prefStore.updateFollowSystemMode(follow)
    }
}