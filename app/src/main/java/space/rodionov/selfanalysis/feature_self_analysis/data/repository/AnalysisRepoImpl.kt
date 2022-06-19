package space.rodionov.selfanalysis.feature_self_analysis.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDao
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDatabase
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo

class AnalysisRepoImpl(
    private val analysisDao: AnalysisDao
): AnalysisRepo {

    override suspend fun deleteAnalysis(analysis: Analysis) {
        analysisDao.delete(analysis.toAnalysisEntity())
    }

    override suspend fun updateAnalysis(analysis: Analysis) {
        analysisDao.update(analysis.toAnalysisEntity())
    }

    override suspend fun insertAnalysis(analysis: Analysis) {
        analysisDao.insert(analysis.toAnalysisEntity())
    }

    override suspend fun deleteAll() {
        analysisDao.deleteAll()
    }

    override fun getAllAnalysis(): Flow<List<Analysis>> {
       return analysisDao.getAllNotes().map { list ->
           list.map {
               it.toAnalysis()
           }
       }
    }

    override fun getAnalysisBy(searchQuery: String, emotionFilter: String): Flow<List<Analysis>> {
        return analysisDao.getNotes(searchQuery, emotionFilter).map { list ->
            list.map {
                it.toAnalysis()
            }
        }
    }
}