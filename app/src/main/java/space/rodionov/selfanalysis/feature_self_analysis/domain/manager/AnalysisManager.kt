package space.rodionov.selfanalysis.feature_self_analysis.domain.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.OrderType

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
        searchQuery: String?,
        emotionFilter: String?,
        noteOrder: NoteOrder
    ): Flow<List<Analysis>> {
        return analysisRepo.getAnalysisBy(
            searchQuery, emotionFilter
        ).map { list ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    if (noteOrder is NoteOrder.Situation) list.sortedBy { it.situation } else list.sortedBy { it.date }
                }
                else -> {
                    if (noteOrder is NoteOrder.Situation) list.sortedByDescending { it.situation } else list.sortedByDescending { it.date }
                }
            }
        }
    }

}