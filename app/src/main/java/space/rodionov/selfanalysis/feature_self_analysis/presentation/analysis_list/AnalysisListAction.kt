package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder

sealed class AnalysisListAction {
    data class SearchQueryChange(val newQuery: String): AnalysisListAction()
    data class EmotionFilterChange(val newEmoFilter: String): AnalysisListAction()
    data class Order(val noteOrder: NoteOrder): AnalysisListAction()
    data class DeleteNote(val analysis: Analysis): AnalysisListAction()
    object RestoreNote: AnalysisListAction()
    object ToggleOrderSection: AnalysisListAction()
}
