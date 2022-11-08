package com.pehom.theshi.presentation.screens.testscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CardTestResult(
    task: MutableState<Task>,
    viewModel: MainViewModel,
    isWrongAnswersShown: MutableState<Boolean>,
    taskRoomItem: MutableState<TaskRoomItem>,
    isTestPaused: MutableState<Boolean>
) {
    val currentTask = remember{ task }
    val result = ((1-currentTask.value.wrongTestAnswers.size.toFloat()/currentTask.value.vocabulary.items.size.toFloat())*100).toInt()
    val currentVocabulary = currentTask.value.vocabulary
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp), elevation = 5.dp){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(15.dp), contentAlignment = Alignment.Center){
                Text(stringResource(id = R.string.test_result), fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(15.dp), contentAlignment = Alignment.Center){
                Text(text = "$result %", fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(15.dp), contentAlignment = Alignment.CenterStart){
                Text(text = stringResource(id = R.string.correct_answers) + "  ${currentTask.value.vocabulary.items.size - currentTask.value.wrongTestAnswers.size}", fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(15.dp)
                .clickable {
                    if (currentTask.value.wrongTestAnswers.isNotEmpty()) {
                        isWrongAnswersShown.value = true
                    }
                }, contentAlignment = Alignment.CenterStart){
                Text(text = stringResource(id = R.string.wrong_answers) + " " +
                        " ${currentTask.value.wrongTestAnswers.size}", fontSize = 22.sp)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        viewModel.currentTask.value.currentTestItem.value = 0
                        viewModel.currentTask.value.isTestGoing.value = false
                        viewModel.currentTask.value.wrongTestAnswers.clear()
                        val updateTaskRoomItem = taskRoomItem.value
                        updateTaskRoomItem.progress = viewModel.currentTask.value.progress
                        updateTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
                        updateTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
                        updateTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
                        updateTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            Constants.REPOSITORY.updateTaskRoomItem(updateTaskRoomItem){
                                isTestPaused.value = true
                            }
                        }
                        viewModel.currentTask.value.testRefresh()
                    }) {
                    Text(text = stringResource(id = R.string.retry))
                }
                Button(
                    onClick = {
                        if ( result == 100) {  // synchronizing local wordbook with fsWordbook
                            viewModel.useCases.addVocabularyToWordbookFsUseCase.execute(currentVocabulary, viewModel.user.value.fsId){}
                            viewModel.useCases.addVocabularyToWordbookRoomUseCase.execute(viewModel){}
                        }
                        taskRoomItem.value.progress = result
                        taskRoomItem.value.currentTestItem = 0
                        taskRoomItem.value.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
                        viewModel.useCases.updateTaskFsUseCase.execute(viewModel, taskRoomItem){}


                      //  viewModel.currentTask.value.progress = result
                     //   taskRoomItem.value.wrongTestAnswers.clear()
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            Constants.REPOSITORY.updateTaskRoomItem(taskRoomItem.value){
                              //  viewModel.viewModelScope.launch(Dispatchers.Main) {
                                    viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                                    viewModel.currentTask.value.currentTestItem.value = 0
                                    viewModel.currentTask.value.isTestGoing.value = false
                                    viewModel.currentTask.value.wrongTestAnswers.clear()
                                    viewModel.currentTask.value.testRefresh()
                                //  }
                            }
                        }
                    }) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}