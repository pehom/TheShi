package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun DialogTasksFilter(
    viewModel: MainViewModel,
    _dialogState: MutableState<Boolean>,
){
    val dialogState = remember{_dialogState }
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                DialogTasksFilterContent(
                    viewModel = viewModel,
                    dialogState = _dialogState
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
private fun DialogTasksFilterContent(
    viewModel: MainViewModel,
    dialogState: MutableState<Boolean>
){
        Card {
            Column(

            ) {
                Box(
                    modifier = Modifier.padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart){
                    Text(text = stringResource(id = R.string.filter) + ":")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
                        ,
                        contentAlignment = Alignment.CenterStart){
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    viewModel.tasksFilterState.value = Constants.FILTER_ALL
                                    dialogState.value = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                    checked =viewModel.tasksFilterState.value == Constants.FILTER_ALL,
                                    onCheckedChange ={
                                        viewModel.tasksFilterState.value = Constants.FILTER_ALL
                                        dialogState.value = false
                                    },
                                //    enabled = false
                                )
                            Box(
                                modifier = Modifier
                                   // .padding(start = 10.dp)
                                    ,
                                contentAlignment = Alignment.CenterStart){
                                Text(text = stringResource(id = R.string.filter_all))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart){
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    viewModel.tasksFilterState.value = Constants.FILTER_IN_PROGRESS
                                    dialogState.value = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                    checked =viewModel.tasksFilterState.value == Constants.FILTER_IN_PROGRESS,
                                    onCheckedChange ={
                                        viewModel.tasksFilterState.value = Constants.FILTER_IN_PROGRESS
                                        dialogState.value = false
                                    },
                               //     enabled = false
                            )
                            Box(
                                modifier = Modifier
                                   // .padding(start = 10.dp)
                                    ,
                                contentAlignment = Alignment.CenterStart){
                                Text(text = stringResource(id = R.string.filter_in_progress))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart){
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    viewModel.tasksFilterState.value = Constants.FILTER_FINISHED
                                    dialogState.value = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                    checked =viewModel.tasksFilterState.value == Constants.FILTER_FINISHED,
                                    onCheckedChange ={
                                        viewModel.tasksFilterState.value = Constants.FILTER_FINISHED
                                        dialogState.value = false },
                               //     enabled = false
                            )
                            Box(
                                modifier = Modifier
                                   // .padding(start = 10.dp)
                                    ,
                                contentAlignment = Alignment.CenterStart){
                                Text(text = stringResource(id = R.string.filter_finished) )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart){
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                viewModel.tasksFilterState.value = Constants.FILTER_CANCELLED
                                dialogState.value = false
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                    checked =viewModel.tasksFilterState.value == Constants.FILTER_CANCELLED,
                                    onCheckedChange ={
                                        viewModel.tasksFilterState.value = Constants.FILTER_CANCELLED
                                        dialogState.value = false
                                    },
                                //    enabled = false
                            )
                            Box(
                                modifier = Modifier
                                   // .padding(start = 10.dp)
                                    ,
                                contentAlignment = Alignment.CenterStart){
                                Text(text = stringResource(id = R.string.filter_cancelled) )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
}