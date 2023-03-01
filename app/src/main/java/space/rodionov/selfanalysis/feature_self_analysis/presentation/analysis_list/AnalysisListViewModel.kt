package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder
import javax.inject.Inject

@HiltViewModel
class AnalysisListViewModel @Inject constructor(
    private val analysisManager: AnalysisManager,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _state = mutableStateOf(AnalysisListState())
    val state: State<AnalysisListState> = _state

//    private val _eventFlow = MutableSharedFlow<UIEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAnalysisList(state.value.searchQuery, state.value.emotionFilter, state.value.noteOrder)
    }

    private var getAnalysisListJob: Job? = null
    private fun getAnalysisList(query: String, emotionFilter: String, noteOrder: NoteOrder) {
        getAnalysisListJob?.cancel() //todo а здесь вообще нужна эта Job-а?
        getAnalysisListJob = analysisManager.getAnalysisBy(query, emotionFilter, noteOrder)
            .onEach {
                _state.value = state.value.copy(
                    analysisList = it
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: AnalysisListAction) {
        when (action) {
            is AnalysisListAction.SearchQueryChange -> {
                onSearch(action.newQuery)
            }
            is AnalysisListAction.EmotionFilterChange -> {
                onEmoFilter(action.newEmoFilter)
            }
            is AnalysisListAction.Order -> {
                onOrderChange(action.noteOrder)
            }
            is AnalysisListAction.DeleteNote -> {

            }
            is AnalysisListAction.RestoreNote -> {

            }
            is AnalysisListAction.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private var searchJob: Job? = null
    private fun onSearch(query: String) {
        _state.value = state.value.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            getAnalysisList(query, state.value.emotionFilter, state.value.noteOrder)
        }
    }

    private fun onEmoFilter(emoFilter: String) {
        _state.value = state.value.copy(emotionFilter = emoFilter)
        getAnalysisList(state.value.searchQuery, emoFilter, state.value.noteOrder)
    }

    private fun onOrderChange(order: NoteOrder) {
        if (state.value.noteOrder::class == order::class && state.value.noteOrder.orderType == order.orderType) {
            return
        }
        _state.value = state.value.copy(noteOrder = order)
        getAnalysisList(state.value.searchQuery, state.value.emotionFilter, order)
    }


    sealed class UIEvent {
        data class ShowSnackbar(val msg: String) : UIEvent()
    }
}