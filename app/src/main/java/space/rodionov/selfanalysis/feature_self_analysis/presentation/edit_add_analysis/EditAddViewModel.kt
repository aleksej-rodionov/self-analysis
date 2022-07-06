package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import javax.inject.Inject

@HiltViewModel
class EditAddViewModel @Inject constructor(
    private val analysisManager: AnalysisManager,
    private val prefManager: PrefManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(EditAddAnalysisState())
    val state: State<EditAddAnalysisState> = _state

//    private val _eventFlow = MutableSharedFlow<EditAddEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()

    fun onAction(action :EditAddAction) {
        when (action) {
            is EditAddAction.SaveNote -> {
                //todo все как в CleanArchitectureNoteApp
            }
        }
    }







    sealed class EditAddEvent {
        data class ShowSnackbar(val msg: String) : EditAddEvent()
    }
}





