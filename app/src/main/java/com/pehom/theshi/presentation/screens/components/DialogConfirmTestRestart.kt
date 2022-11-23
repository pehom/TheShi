package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DialogConfirmTestRestart(
    viewModel: MainViewModel,
    _dialogState: MutableState<Boolean>,
    taskScreenState: MutableState<Int>,
    navItemState: MutableState<TaskBottomNavItem>
){
    val dialogState = remember{_dialogState }
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                DialogConfirmTestRestartContent(
                    viewModel = viewModel,
                    dialogState = dialogState,
                    taskScreenState = taskScreenState,
                    navItemState = navItemState
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}

@Composable
private fun DialogConfirmTestRestartContent(
    viewModel: MainViewModel,
    dialogState: MutableState<Boolean>,
    taskScreenState: MutableState<Int>,
    navItemState: MutableState<TaskBottomNavItem>
){
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier.height(250.dp)
         //   .padding(20.dp)
        ,
        elevation = 5.dp){
        Box(contentAlignment = Alignment.Center){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center){
                    Text(text = stringResource(id = R.string.restart_test_alert))
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row() {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center){
                        Button(onClick = {
                            dialogState.value = false
                        }) {
                            Icon(painterResource(id = R.drawable.ic_baseline_cancel_mk2_24), contentDescription = "back")
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center){
                        Button(onClick = {
                            scope.launch(Dispatchers.IO) {
                                viewModel.currentTask.value.currentTestItem.value = 0
                                viewModel.currentTask.value.wrongTestAnswers.clear()
                                viewModel.currentTask.value.setReadyForTest()
                                val updateTaskRoomItem = viewModel.currentTaskRoomItem.value
                                updateTaskRoomItem.progress = viewModel.currentTask.value.progress
                                updateTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
                                updateTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
                                updateTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
                                updateTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    Constants.REPOSITORY.updateTaskRoomItem(updateTaskRoomItem){
                                        viewModel.viewModelScope.launch(Dispatchers.Main){
                                            Log.d("rrrr", "navItemState = ${navItemState.value.screen}")
                                            taskScreenState.value = navItemState.value.screen
                                            dialogState.value = false
                                        }
                                    }
                                }
                            }
                        }) {
                            Icon(painterResource(id = R.drawable.ic_baseline_done_24), contentDescription = "done" )
                        }
                    }
                }
            }
        }
    }
}