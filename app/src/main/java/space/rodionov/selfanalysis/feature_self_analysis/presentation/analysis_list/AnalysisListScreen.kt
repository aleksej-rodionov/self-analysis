package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import space.rodionov.selfanalysis.feature_self_analysis.presentation.Screen
import space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list.components.OrderSection
import space.rodionov.selfanalysis.util.Constants.EMPTY
import space.rodionov.selfanalysis.util.Constants.TAG_DEBUG

//@ExperimentalAnimationApi
@Composable
fun AnalysisListScreen(
    navController: NavController,
    viewModel: AnalysisListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.EditAddAnalysisScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My notes",
                        style = MaterialTheme.typography.h6
                    )
                    IconButton(
                        onClick = {
                            viewModel.onAction(AnalysisListAction.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        noteOrder = state.noteOrder,
                        onOrderChange = {
                            viewModel.onAction(AnalysisListAction.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(

                    value = viewModel.searchQuery.value,
                    onValueChange = {
                        viewModel.onAction(AnalysisListAction.SearchQueryChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search...") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                ) {
                    items(state.analysisList.size) { i ->
                        val analysis = state.analysisList[i]
                        if(i > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        AnalysisItem(
                            analysis = analysis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.EditAddAnalysisScreen.route +
                                                "?analysisId=${analysis.id}"
                                    )
                                }
                        )
                        if(i < state.analysisList.size - 1) {
                            Divider()
                        }
                    }
                }
                if(state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}