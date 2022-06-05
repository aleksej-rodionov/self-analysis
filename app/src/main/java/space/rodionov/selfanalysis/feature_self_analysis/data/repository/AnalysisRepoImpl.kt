package space.rodionov.selfanalysis.feature_self_analysis.data.repository

import kotlinx.coroutines.flow.Flow
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDatabase
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo

class AnalysisRepoImpl(
    private val analysisDatabase: AnalysisDatabase
): AnalysisRepo {

    override suspend fun deleteAnalysis(analysis: Analysis) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAnalysis(analysis: Analysis) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAnalysis(analysis: Analysis) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getAllAnalysis(): Flow<List<Analysis>> {
        TODO("Not yet implemented")
    }

    override fun getAnalysisBy(searchQuery: String, emotionFilter: String): Flow<List<Analysis>> {
        TODO("Not yet implemented")
    }
}