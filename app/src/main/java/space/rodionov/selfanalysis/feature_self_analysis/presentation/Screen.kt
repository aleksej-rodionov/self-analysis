package space.rodionov.selfanalysis.feature_self_analysis.presentation

sealed class Screen(val route: String) {
    object AnalysisListScreen: Screen("analysis_list")
    object EditAddAnalysisScreen: Screen("edit_add_analysis")
}
