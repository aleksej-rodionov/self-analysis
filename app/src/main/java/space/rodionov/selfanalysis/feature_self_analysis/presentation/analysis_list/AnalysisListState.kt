package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis

data class AnalysisListState(
    val analysisList: List<Analysis> = emptyList(),
    val isLoading: Boolean = false
)
