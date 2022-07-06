package space.rodionov.selfanalysis.feature_self_analysis.edit_add_analysis.presentation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.EditAddAction
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.EditAddViewModel

@Composable
fun EditAddAnalysisScreen(
    navController: NavController,
    viewModel: EditAddViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val state = viewModel.state.value



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAction(EditAddAction.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        }
    ) {

    }
}






