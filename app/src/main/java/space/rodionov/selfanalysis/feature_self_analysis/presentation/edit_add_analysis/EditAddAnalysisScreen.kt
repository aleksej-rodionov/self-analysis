package space.rodionov.selfanalysis.feature_self_analysis.edit_add_analysis.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

            Text(modifier = Modifier.align(Alignment.End), text = state.date, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.situation,
                onValueChange = {
                    viewModel.onAction(EditAddAction.SituationChange(it))
                },
                isError = state.situation.isError != null,
                modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                    state.situation.hint
                }
            )
            Spacer(modifier = Modifier.height(8.dp))


            // todo нарисовать эти ёбаный поля. Но сначала Во вьюмодели логику экшнов?
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






