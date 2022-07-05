package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.NoteOrder
import space.rodionov.selfanalysis.util.Constants.EMPTY
import space.rodionov.selfanalysis.util.Constants.TAG_DEBUG
import javax.inject.Inject

@HiltViewModel
class AnalysisListViewModel @Inject constructor(
    private val analysisManager: AnalysisManager,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    private val _emotionFilter = mutableStateOf("")
    val emotionFilter: State<String> = _emotionFilter

    private val _state = mutableStateOf(AnalysisListState())
    val state: State<AnalysisListState> = _state

//    private val _eventFlow = MutableSharedFlow<UIEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAnalysisList(searchQuery.value, emotionFilter.value, state.value.noteOrder)
    }

    private var getAnalysisListJob: Job? = null
    private fun getAnalysisList(query: String?, emotionFilter: String?, noteOrder: NoteOrder) { // todo добавить третий аргумент ноутОрдер
        getAnalysisListJob?.cancel()
        getAnalysisListJob = analysisManager.getAnalysisBy(query, emotionFilter, noteOrder)
            .onEach {
                _state.value = state.value.copy(
                    analysisList = it,
                    noteOrder = noteOrder
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
                getAnalysisList(searchQuery.value, action.newEmoFilter, state.value.noteOrder)
            }
            is AnalysisListAction.Order -> {
                if (state.value.noteOrder::class == action.noteOrder::class &&
                    state.value.noteOrder.orderType == action.noteOrder.orderType
                ) {
                    return
                }
                getAnalysisList(searchQuery.value, emotionFilter.value, action.noteOrder)
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
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            getAnalysisList(query, emotionFilter.value ?: EMPTY, state.value.noteOrder) // todo implement emotion filter
        }
    }


    sealed class UIEvent {
        data class ShowSnackbar(val msg: String) : UIEvent()
    }
}