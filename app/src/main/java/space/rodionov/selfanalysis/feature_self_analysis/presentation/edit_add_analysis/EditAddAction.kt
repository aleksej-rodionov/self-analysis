package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import androidx.compose.ui.focus.FocusState

sealed class EditAddAction {

    data class SituationChange(val value: String): EditAddAction()
    data class SituationFocusChange(val focusState: FocusState): EditAddAction()


    object SaveNote: EditAddAction()
}
