package com.pehom.theshi.presentation.screens.learningscreen

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.screens.testscreen.CardStart
import com.pehom.theshi.presentation.screens.testscreen.CardTest
import com.pehom.theshi.presentation.screens.testscreen.CardTestResult
import com.pehom.theshi.presentation.screens.testscreen.CardWrongAnswers
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LearningScreen(
    viewModel: MainViewModel,
    taskRoomItem: MutableState<TaskRoomItem>,
    tts: TextToSpeech?
    ) {
    Log.d("ppp", "LearningScreen is on")

    val currentTask = remember { mutableStateOf(viewModel.currentTask.value) }
    val wordsRemain = remember {currentTask.value.learningWordsRemain}
    val currentWordDisplay = remember { currentTask.value.currentLearningWordDisplay }
    val isRestarted = remember { mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.1f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            elevation = 5.dp) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.padding( 5.dp),
                    contentAlignment = Alignment.Center) {
                    Text(text = currentTask.value.title)
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        ,contentAlignment = Alignment.CenterStart){
                        Button(
                            onClick = {
                                viewModel.currentTask.value.currentLearningItem.value = 0
                                viewModel.currentTask.value.setReadyForLearning()
                           //     viewModel.currentTaskRoomItem.value.currentLearningItem = 0
//                                val updateTaskRoomItem = taskRoomItem.value
//                                updateTaskRoomItem.progress = viewModel.currentTask.value.progress
//                                updateTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
//                                updateTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
//                                updateTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
//                                updateTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
//                                viewModel.viewModelScope.launch(Dispatchers.IO) {
//                                    Constants.REPOSITORY.updateTaskRoomItem(updateTaskRoomItem){
//                                        isRestarted.value = true
//                                    }
//                                }
                            }) {
                            Text(text = stringResource(id = R.string.retry), fontSize = 12.sp)                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center){
                        Text(text = stringResource(id = R.string.words_remain) + "   ${wordsRemain.value}")
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                }
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            elevation = 5.dp) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(5f)
                    .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center) {
                    Text(text =  currentWordDisplay.value,
                        fontSize = 22.sp)
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(2f)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                    contentAlignment = Alignment.CenterEnd) {
                    IconButton(
                        enabled = currentTask.value.learningWordsRemain.value > 0,
                        onClick = {

                        tts!!.speak(
                            currentTask.value.currentLearningWord.value.trans,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            ""
                        )
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_speaker), contentDescription = "pronunciation")
                    }
                }
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                // .padding(15.dp)
                .weight(4f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
        ){
            CardLearning(task = currentTask, isRestarted)
        }
    }
}

