package com.pehom.theshi.presentation.screens.testscreen

import android.util.Log
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
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R

@Composable
fun CardTestResult(
    task: MutableState<Task>,
    viewModel: MainViewModel,
    isWrongAnswersShown: MutableState<Boolean>,
) {
    val taskNumber = viewModel.currentTaskNumber.value
    val currentTask = remember{ task }
    val result = ((currentTask.value.correctTestAnswers.size.toFloat()/currentTask.value.vocabulary.items.size.toFloat())*100).toInt()
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
                Text(text = stringResource(id = R.string.correct_answers) + "  ${currentTask.value.correctTestAnswers.size}", fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(15.dp)
                .clickable {
                    if (currentTask.value.wrongTestAnswers.isNotEmpty()) {
                        isWrongAnswersShown.value = true
                        Log.d("buba", "TestActivity: result = $result  taskNumber = $taskNumber")
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
                        // viewModel.tasks[taskNumber].progress = result
                        viewModel.currentTask.value.currentTestItem.value = 0
                        viewModel.currentTask.value.isTestGoing.value = false
                        viewModel.currentTask.value.wrongTestAnswers.clear()
                        viewModel.currentTask.value.correctTestAnswers.clear()
                        viewModel.currentTask.value.testRefresh()
                        viewModel.screenState.value = viewModel.MODE_TEST_SCREEN
                    }) {
                    Text(text = stringResource(id = R.string.retry))
                }
                Button(
                    onClick = {
                        viewModel.currentTask.value.currentTestItem.value = 0
                        viewModel.currentTask.value.isTestGoing.value = false
                        viewModel.currentTask.value.wrongTestAnswers.clear()
                        viewModel.currentTask.value.correctTestAnswers.clear()
                        viewModel.currentTask.value.testRefresh()
                        viewModel.currentTask.value.progress = result
                        viewModel.tasksInfo[viewModel.currentTaskNumber.value].progress = result
                       /* viewModel.tasksInfo.forEachIndexed(){ index, item ->
                            Log.d("zzz", "task[$index].progress = ${item.progress}")
                        }*/
                        viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                    }) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}