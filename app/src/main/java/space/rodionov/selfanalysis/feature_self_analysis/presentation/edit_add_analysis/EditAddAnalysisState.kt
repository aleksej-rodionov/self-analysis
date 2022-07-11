package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.util.Constants

data class EditAddAnalysisState(
    val analysis: Analysis? = null,
    val situation: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Situation ..."),
    val emotions: List<String> = listOf(), //todo list поменять на set
    val feelings: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Feelings ..."),
    val inTheBody: AnalysisTextFieldState = AnalysisTextFieldState(hint = "In the body ..."),
    val wantedToDo: AnalysisTextFieldState = AnalysisTextFieldState(hint = "What I wanted to do ..."),
    val whatDoesTheFeelingMean: AnalysisTextFieldState = AnalysisTextFieldState(hint = "What does the feeling mean ..."),
    val thoughts: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Thoughts ..."),
    val fears: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Fears ..."),
    val askingFromHP: AnalysisTextFieldState = AnalysisTextFieldState(hint = "What I ask from the Higher Power ..."),
    val innerCritic: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Inner critic's voice ..."),
    val lovingParent: AnalysisTextFieldState = AnalysisTextFieldState(hint = "Loving parent's voice ..."),
    val date: String = "EMPTY",
    val isLoading: Boolean = false,
    val mode: Int = Constants.DEFAULT_INT,
    val language: Int = Constants.DEFAULT_INT
)

data class AnalysisTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val isError: String? = null
)







