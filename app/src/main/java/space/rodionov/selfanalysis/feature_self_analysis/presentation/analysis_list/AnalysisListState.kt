package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.util.Constants.DEFAULT_INT

data class AnalysisListState(
    val analysisList: List<Analysis> = emptyList(),
    val isLoading: Boolean = false,
    val mode: Int = DEFAULT_INT,
    val language: Int = DEFAULT_INT
)
