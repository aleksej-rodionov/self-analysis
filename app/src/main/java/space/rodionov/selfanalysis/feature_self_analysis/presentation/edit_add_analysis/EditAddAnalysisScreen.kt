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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.EditAddAction
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.EditAddViewModel
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.custom.CustomChipGroup

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

            OutlinedTextField(
                value = state.situation.text,
                onValueChange = {
                    viewModel.onAction(EditAddAction.SituationChange(it))
                },
                isError = true/*state.situation.isError != null*/,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    state.situation.hint
                },
                label = {
                    Text(text = stringResource(id = R.string.situation))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomChipGroup(
//                onSelectedChanged = {
//                    viewModel.onAction(EditAddAction.EmotionsChange())
//                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.feelings.text,
                onValueChange = {
                    viewModel.onAction(EditAddAction.SituationChange(it))
                },
                isError = state.feelings.isError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    state.feelings.hint
                },
                label = {
                    Text(text = stringResource(id = R.string.feelings))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

//            TextField(
//                value = state.inTheBody.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.inTheBody.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.inTheBody.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.wantedToDo.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.wantedToDo.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.wantedToDo.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.whatDoesTheFeelingMean.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.whatDoesTheFeelingMean.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.whatDoesTheFeelingMean.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.thoughts.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.thoughts.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.thoughts.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.fears.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.fears.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.fears.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.askingFromHP.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.askingFromHP.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.askingFromHP.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.innerCritic.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.innerCritic.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.innerCritic.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextField(
//                value = state.lovingParent.text,
//                onValueChange = {
//                    viewModel.onAction(EditAddAction.SituationChange(it))
//                },
//                isError = state.lovingParent.isError != null,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    state.lovingParent.hint
//                }
//            )
//            Spacer(modifier = Modifier.height(8.dp))


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






