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

            is EditAddAction.SituationChange -> {
                _state.value = state.value.copy(situation = state.value.situation.copy(text = action.value))
            }
            is EditAddAction.SituationFocusChange -> {
                _state.value = state.value.copy(situation = state.value.situation.copy(isHintVisible = !action.focusState.isFocused && state.value.situation.text.isBlank()))
            }

            is EditAddAction.EmotionsChange -> {
                _state.value = state.value.copy(emotions = action.value)
            }

            is EditAddAction.FeelingsChange -> {
                _state.value = state.value.copy(feelings = state.value.feelings.copy(text = action.value))
            }
            is EditAddAction.FeelingsFocusChange -> {
                _state.value = state.value.copy(feelings = state.value.feelings.copy(isHintVisible = !action.focusState.isFocused && state.value.feelings.text.isBlank()))
            }

            is EditAddAction.InTheBodyChange -> {
                _state.value = state.value.copy(inTheBody = state.value.inTheBody.copy(text = action.value))
            }
            is EditAddAction.InTheBodyFocusChange -> {
                _state.value = state.value.copy(inTheBody = state.value.inTheBody.copy(isHintVisible = !action.focusState.isFocused && state.value.inTheBody.text.isBlank()))
            }

            is EditAddAction.WantedToDoChange -> {
                _state.value = state.value.copy(wantedToDo = state.value.wantedToDo.copy(text = action.value))
            }
            is EditAddAction.WantedToDoFocusChange -> {
                _state.value = state.value.copy(wantedToDo = state.value.wantedToDo.copy(isHintVisible = !action.focusState.isFocused && state.value.wantedToDo.text.isBlank()))
            }

            is EditAddAction.WhatDoesTheFeelingMeanChange -> {
                _state.value = state.value.copy(whatDoesTheFeelingMean = state.value.whatDoesTheFeelingMean.copy(text = action.value))
            }
            is EditAddAction.WhatDoesTheFeelingMeanFocusChange -> {
                _state.value = state.value.copy(whatDoesTheFeelingMean = state.value.whatDoesTheFeelingMean.copy(isHintVisible = !action.focusState.isFocused && state.value.whatDoesTheFeelingMean.text.isBlank()))
            }

            is EditAddAction.ThoughtsChange -> {
                _state.value = state.value.copy(thoughts = state.value.thoughts.copy(text = action.value))
            }
            is EditAddAction.ThoughtsFocusChange -> {
                _state.value = state.value.copy(thoughts = state.value.thoughts.copy(isHintVisible = !action.focusState.isFocused && state.value.thoughts.text.isBlank()))
            }

            is EditAddAction.FearsChange -> {
                _state.value = state.value.copy(fears = state.value.fears.copy(text = action.value))
            }
            is EditAddAction.FearsFocusChange -> {
                _state.value = state.value.copy(fears = state.value.fears.copy(isHintVisible = !action.focusState.isFocused && state.value.fears.text.isBlank()))
            }

            is EditAddAction.AskingFromHPChange -> {
                _state.value = state.value.copy(askingFromHP = state.value.askingFromHP.copy(text = action.value))
            }
            is EditAddAction.AskingFromHPFocusChange -> {
                _state.value = state.value.copy(askingFromHP = state.value.askingFromHP.copy(isHintVisible = !action.focusState.isFocused && state.value.askingFromHP.text.isBlank()))
            }

            is EditAddAction.InnerCriticChange -> {
                _state.value = state.value.copy(innerCritic = state.value.innerCritic.copy(text = action.value))
            }
            is EditAddAction.InnerCriticFocusChange -> {
                _state.value = state.value.copy(innerCritic = state.value.innerCritic.copy(isHintVisible = !action.focusState.isFocused && state.value.innerCritic.text.isBlank()))
            }

            is EditAddAction.LovingParentChange -> {
                _state.value = state.value.copy(lovingParent = state.value.lovingParent.copy(text = action.value))
            }
            is EditAddAction.LovingParentFocusChange -> {
                _state.value = state.value.copy(lovingParent = state.value.lovingParent.copy(isHintVisible = !action.focusState.isFocused && state.value.lovingParent.text.isBlank()))
            }



            // todo other actions




            is EditAddAction.SaveNote -> {
                viewModelScope.launch {
                    try {
                        analysisManager.insertAnalysis(
                            Analysis(
                                situation = state.value.situation.text,
                                emotions = state.value.emotions,
                                feelings = state.value.feelings.text,
                                inTheBody = state.value.inTheBody.text,
                                wantedToDo = state.value.wantedToDo.text,
                                whatDoesTheFeelingMean = state.value.whatDoesTheFeelingMean.text,
                                thoughts = state.value.thoughts.text,
                                fears = state.value.fears.text,
                                askingFromHP = state.value.askingFromHP.text,
                                innerCritic = state.value.innerCritic.text,
                                lovingParent = state.value.lovingParent.text,
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





