package space.rodionov.selfanalysis.feature_self_analysis.domain.manager

import kotlinx.coroutines.flow.Flow
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo

class AnalysisManager(
    private val analysisRepo: AnalysisRepo
) {

    suspend fun deleteAnalysis(analysis: Analysis) {
        analysisRepo.deleteAnalysis(analysis)
    }

    suspend fun updateAnalysis(analysis: Analysis) {
        analysisRepo.updateAnalysis(analysis)
    }

    suspend fun insertAnalysis(analysis: Analysis) {
        analysisRepo.insertAnalysis(analysis)
    }

    suspend fun deleteAll() {
        analysisRepo.deleteAll()
    }

    fun getAllAnalysis(): Flow<List<Analysis>> {
        return analysisRepo.getAllAnalysis()
    }

    fun getAnalysisBy(
        searchQuery: String,
        emotionFilter: String
    ): Flow<List<Analysis>> {
        return analysisRepo.getAnalysisBy(
            searchQuery, emotionFilter
        )
    }

}