package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.OrderType
import space.rodionov.selfanalysis.util.Constants.DEFAULT_INT
import space.rodionov.selfanalysis.util.Constants.EMPTY

data class AnalysisListState(
    val analysisList: List<Analysis> = emptyList(),
    val searchQuery: String = EMPTY,
    val emotionFilter: String = EMPTY,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isLoading: Boolean = false,
    val mode: Int = DEFAULT_INT,
    val language: Int = DEFAULT_INT
)
