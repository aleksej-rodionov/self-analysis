package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis

import androidx.compose.ui.focus.FocusState

sealed class EditAddAction {

    data class SituationChange(val value: String): EditAddAction()
    data class SituationFocusChange(val focusState: FocusState): EditAddAction()

    data class EmotionsChange(val value: List<String>): EditAddAction() //todo List поменять на Set

    data class FeelingsChange(val value: String): EditAddAction()
    data class FeelingsFocusChange(val focusState: FocusState): EditAddAction()

    data class InTheBodyChange(val value: String): EditAddAction()
    data class InTheBodyFocusChange(val focusState: FocusState): EditAddAction()

    data class WantedToDoChange(val value: String): EditAddAction()
    data class WantedToDoFocusChange(val focusState: FocusState): EditAddAction()

    data class WhatDoesTheFeelingMeanChange(val value: String): EditAddAction()
    data class WhatDoesTheFeelingMeanFocusChange(val focusState: FocusState): EditAddAction()

    data class ThoughtsChange(val value: String): EditAddAction()
    data class ThoughtsFocusChange(val focusState: FocusState): EditAddAction()

    data class FearsChange(val value: String): EditAddAction()
    data class FearsFocusChange(val focusState: FocusState): EditAddAction()

    data class AskingFromHPChange(val value: String): EditAddAction()
    data class AskingFromHPFocusChange(val focusState: FocusState): EditAddAction()

    data class InnerCriticChange(val value: String): EditAddAction()
    data class InnerCriticFocusChange(val focusState: FocusState): EditAddAction()

    data class LovingParentChange(val value: String): EditAddAction()
    data class LovingParentFocusChange(val focusState: FocusState): EditAddAction()

    // todo other actions


    object SaveNote: EditAddAction()
}
