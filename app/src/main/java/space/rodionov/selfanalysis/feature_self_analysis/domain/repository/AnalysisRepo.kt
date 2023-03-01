package space.rodionov.selfanalysis.feature_self_analysis.domain.repository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder

interface AnalysisRepo {

    suspend fun deleteAnalysis(analysis: Analysis)

    suspend fun updateAnalysis(analysis: Analysis)

    suspend fun insertAnalysis(analysis: Analysis)

    suspend fun deleteAll()

    fun getAllAnalysis(): Flow<List<Analysis>>

    fun getAnalysisBy(
        searchQuery: String?,
        emotionFilter: String?
    ): Flow<List<Analysis>>
}