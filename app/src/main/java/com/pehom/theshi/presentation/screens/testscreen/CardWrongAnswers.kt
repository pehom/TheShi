package com.pehom.theshi.presentation.screens.testscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R

@Composable
fun CardWrongAnswers(
    task: MutableState<Task>,
    viewModel: MainViewModel,
    isWrongAnswersShown: MutableState<Boolean>
) {
    val taskNumber = viewModel.currentTaskNumber.value
    val currentTask = remember{ task }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp), elevation = 5.dp){
        Column(Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp) ) {
                itemsIndexed(currentTask.value.wrongTestAnswers.keys.toList()) {index, item ->
                    Text(text = "${index+1}) q = ${currentTask.value.vocabulary.items[item].orig}  answer = ${currentTask.value.wrongTestAnswers[item]} "
                            + " cor.answer = ${currentTask.value.vocabulary.items[item].translation} ")
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { isWrongAnswersShown.value = false }) {
                    Text(text = stringResource(id = R.string.back))
                }
                Button(onClick = {
                    val result = ((currentTask.value.correctTestAnswers.size.toFloat()/currentTask.value.vocabulary.items.size.toFloat())*100).toInt()
                    viewModel.currentTask.value.currentTestItem.value = 0
                    viewModel.currentTask.value.isTestGoing.value = false
                    viewModel.currentTask.value.wrongTestAnswers.clear()
                    viewModel.currentTask.value.correctTestAnswers.clear()
                    viewModel.currentTask.value.testRefresh()
                    viewModel.currentTask.value.progress = result
                    viewModel.tasksInfo[viewModel.currentTaskNumber.value].progress = result
                    viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                }) {
                    Text(text = stringResource(id = R.string.next))
                }

            }
        }
    }
}