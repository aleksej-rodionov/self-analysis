package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.util.Constants

data class EditAddAnalysisState(
    val analysis: Analysis? = null,
    val situation: String = "EMPTY",
    val emotions: String = "EMPTY",
    val feelings: String = "EMPTY",
    val inTheBody: String = "EMPTY",
    val wantedToDo: String? = null,
    val whatDoesTheFeelingMean: String? = null,
    val thoughts: String? = null,
    val fears: String? = null,
    val askingFromHP: String? = null,
    val innerCritic: String? = null,
    val lovingParent: String? = null,
    val date: String = "EMPTY",
    val isLoading: Boolean = false,
    val mode: Int = Constants.DEFAULT_INT,
    val language: Int = Constants.DEFAULT_INT
)
