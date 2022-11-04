package com.pehom.theshi.presentation.screens.gamescreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers

@Composable
fun GameScreen(
    viewModel: MainViewModel,
    taskRoomItem: MutableState<TaskRoomItem>

    ) {
    Log.d("ppp", "GameScreen is on")

    val currentTask = remember { viewModel.currentTask }
    val wordsRemain = remember {currentTask.value.taskWordsRemain}
    val currentWord = remember {currentTask.value.currentTaskWord}
    val playingModel = remember { PlayingModel( currentTask.value, taskRoomItem) }
    val currentWordDisplay = remember {currentTask.value.currentTaskWordDisplay}
    val scope = rememberCoroutineScope()
    val hintButtonState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        //  .background(Color.Yellow)
        .wrapContentSize(Alignment.Center),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.5f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = 5.dp
        ){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), contentAlignment = Alignment.Center){
                    Text(text = currentTask.value.title, modifier = Modifier.fillMaxWidth(), fontSize = 20.sp, textAlign = TextAlign.Center)
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp, start = 10.dp, end = 10.dp)
                    ,horizontalArrangement = Arrangement.SpaceBetween
                    , verticalAlignment = Alignment.CenterVertically){
                    Button( onClick = {
                        viewModel.currentTask.value.currentTaskItem.value = 0
                        viewModel.currentTask.value.setReadyForTask()
                        var updateTaskRoomItem = taskRoomItem.value
                        updateTaskRoomItem.progress = viewModel.currentTask.value.progress
                        updateTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
                        updateTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
                        updateTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
                        updateTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            Constants.REPOSITORY.updateTaskRoomItem(updateTaskRoomItem){}
                        }
                    }) {
                        Text(stringResource(id = R.string.retry))
                    }
                    Text(stringResource(id = R.string.words_remain) + "  " + wordsRemain.value)
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(3f)
            .padding(15.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = 5.dp) {
            Column(Modifier.fillMaxSize(),  horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(3f), contentAlignment = Alignment.Center) {
                    Text(text = currentWordDisplay.value, fontSize = 22.sp)
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){

                    val isHintVisible = remember { mutableStateOf(false) }
                    Button(modifier = Modifier.fillMaxHeight(), onClick = {
                        if (!hintButtonState.value){
                            isHintVisible.value = true
                            scope.launch {
                                delay(2000)
                                isHintVisible.value = false
                            }
                        }
                        else {
                            currentTask.value.currentTaskItem.value = 0
                            currentTask.value.taskRefresh()
                            playingModel.task.currentTaskItem.value = 0
                            playingModel.setCards()
                            hintButtonState.value = false
                        }
                    }) {
                        Text(text = if (!hintButtonState.value) stringResource(id = R.string.hint)
                        else stringResource(id = R.string.restart)
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center){
                        Text(text = currentWord.value.trans,
                            fontSize = 22.sp,
                            color = if(isHintVisible.value) Color.Black else Color.Transparent)
                    }
                    Button(modifier = Modifier.fillMaxHeight(), onClick = {
                        if (currentTask.value.currentTaskItem.value <currentTask.value.vocabulary.items.size-1){
                            currentTask.value.currentTaskItem.value++
                            currentTask.value.taskRefresh()
                            Log.d("tag", "playingModel.task.currentVocabularyItem Before++ = ${ playingModel.task.currentTaskItem.value}")

                            //    playingModel.task.currentVocabularyItem.value++
                            playingModel.setCards()
                            Log.d("tag", "currentTask.currentVocabularyItem = ${ currentTask.value.currentTaskItem.value}")
                            Log.d("tag", "playingModel.task.currentVocabularyItem After++ = ${ playingModel.task.currentTaskItem.value}")

                        } else if (currentTask.value.currentTaskItem.value == currentTask.value.vocabulary.items.size-1) {
                            currentTask.value.currentTaskItem.value++
                            currentTask.value.taskRefresh()
                            //  playingModel.task.currentVocabularyItem.value++
                            playingModel.setCards()
                            currentWordDisplay.value = context.getString(R.string.task_is_done)
                            hintButtonState.value = true
                            Log.d("tag", "currentTask.currentVocabularyItem = ${ currentTask.value.currentTaskItem.value}")
                        }
                    }) {
                        Text(text = stringResource(id = R.string.skip))
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(4f)
                .padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            for (i in 0..3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (k in 4 * i..4 * i + 3) {
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(5.dp),
                            elevation = 5.dp,
                        ) {
                            val isVisible = remember { playingModel.cards[k]}.isVisible
                            val isCorrect = remember {playingModel.cards[k]}.correctCardBorder
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .border(
                                        4.dp,
                                        if (isCorrect.value) Color.Yellow else Color.Transparent
                                    )
                                    .clickable {
                                        scope.launch {
                                            val result = playingModel.selectCard(k)
                                            if (result == playingModel.TASKISDONE) {
                                                hintButtonState.value = true
                                                currentWordDisplay.value =
                                                    context.getString(R.string.task_is_done)
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                val textValue = remember { playingModel.variants}
                                Text(
                                    text = textValue[k],
                                    color = if (isVisible.value) Color.Black else Color.Transparent,
                                    maxLines = 3,
                                    overflow = TextOverflow.Clip,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}