package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

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
import space.rodionov.selfanalysis.util.Constants.EMPTY
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

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAnalysisList(searchQuery.value, emotionFilter.value)
    }

    private var getAnalysisListJob: Job? = null
    private fun getAnalysisList(query: String?, emotionFilter: String?) {
        getAnalysisListJob?.cancel()
        getAnalysisListJob = analysisManager.getAnalysisBy(query, emotionFilter)
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
                getAnalysisList(action.newQuery, emotionFilter.value ?: EMPTY)
            }
            is AnalysisListAction.EmotionFilterChange -> {
                getAnalysisList(searchQuery.value, action.newEmoFilter)
            }
            is AnalysisListAction.DeleteNote -> {

            }
            is AnalysisListAction.RestoreNote -> {

            }
            is AnalysisListAction.ToggleOrderSection -> {

            }
        }
    }


//    private var searchJob: Job? = null
//    fun onSearch(query: String) {
//        _searchQuery.value = query
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(700L)
//            analysisManager.getAnalysisBy(query, "") // todo implement emotion filter
//        }
//    }


    sealed class UIEvent {
        data class ShowSnackbar(val msg: String) : UIEvent()
    }
}