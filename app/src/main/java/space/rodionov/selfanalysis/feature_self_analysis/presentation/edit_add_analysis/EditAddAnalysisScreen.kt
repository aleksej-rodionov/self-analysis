package space.rodionov.selfanalysis.feature_self_analysis.edit_add_analysis.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
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
        Column(modifier = Modifier.fillMaxSize()) {

        }
    }



    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditAddViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.msg)
                }
                is EditAddViewModel.UIEvent.NavigateBack -> {
                    navController.navigateUp()
                }
            }
        }
    }
}






