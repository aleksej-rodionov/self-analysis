package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.InvalidAnalysisException
import javax.inject.Inject

@HiltViewModel
class EditAddViewModel @Inject constructor(
    private val analysisManager: AnalysisManager,
    private val prefManager: PrefManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(EditAddAnalysisState())
    val state: State<EditAddAnalysisState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onAction(action: EditAddAction) {
        when (action) {
            is EditAddAction.SaveNote -> {
                viewModelScope.launch {
                    try {
                        analysisManager.insertAnalysis(
                            Analysis(
                                situation = state.value.situation,
                                emotions = state.value.emotions,
                                feelings = state.value.feelings,
                                inTheBody = state.value.inTheBody,
                                wantedToDo = state.value.wantedToDo,
                                whatDoesTheFeelingMean = state.value.whatDoesTheFeelingMean,
                                thoughts = state.value.thoughts,
                                fears = state.value.fears,
                                askingFromHP = state.value.askingFromHP,
                                innerCritic = state.value.innerCritic,
                                lovingParent = state.value.lovingParent,
                                date = state.value.date,
                            )
                        )
                        _eventFlow.emit(UIEvent.NavigateBack)
                    } catch (e: InvalidAnalysisException) {
                        _eventFlow.emit(UIEvent.ShowSnackbar(e.message ?: "Couldn't save note"))
                    }
                }
            }
        }
    }







    sealed class UIEvent {
        data class ShowSnackbar(val msg: String) : UIEvent()
        object NavigateBack : UIEvent()
    }
}





