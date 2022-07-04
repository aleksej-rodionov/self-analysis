package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.util.Constants

data class EditAddAnalysisState(
    val analysis: Analysis? = null,
    val isLoading: Boolean = false,
    val mode: Int = Constants.DEFAULT_INT,
    val language: Int = Constants.DEFAULT_INT
)
