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
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CardWrongAnswers(
    task: MutableState<Task>,
    viewModel: MainViewModel,
    isWrongAnswersShown: MutableState<Boolean>,
    taskRoomItem: MutableState<TaskRoomItem>
) {
    val currentTask = remember { task }
    val currentVocabulary = currentTask.value.vocabulary
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
                            + " cor.answer = ${currentTask.value.vocabulary.items[item].trans} ")
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
                    val result = ((1 - currentTask.value.wrongTestAnswers.size.toFloat()/currentTask.value.vocabulary.items.size.toFloat())*100).toInt()

                    if ( result == 100) {  // synchronizing local wordbook with fsWordbook
                        viewModel.useCases.addVocabularyToWordbookFsUseCase.execute(currentVocabulary, viewModel.user.value.fsId){}
                        viewModel.useCases.addVocabularyToWordbookRoomUseCase.execute(viewModel){}
                    }
                    viewModel.currentTask.value.currentTestItem.value = 0
                    viewModel.currentTask.value.isTestGoing.value = false
                    viewModel.currentTask.value.wrongTestAnswers.clear()
                    viewModel.currentTask.value.testRefresh()
                    viewModel.currentTask.value.progress = result
                    taskRoomItem.value.progress = result
                    viewModel.useCases.updateTaskFsUseCase.execute(viewModel, taskRoomItem){}
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.updateTaskRoomItem(taskRoomItem.value){
                            viewModel.viewModelScope.launch(Dispatchers.Main) {
                                viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                            }
                        }
                    }
                }) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}