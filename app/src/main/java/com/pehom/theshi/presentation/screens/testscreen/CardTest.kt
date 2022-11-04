package com.pehom.theshi.presentation.screens.testscreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.domain.model.Task
import kotlinx.coroutines.delay
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem

@Composable
fun CardTest(
    task: MutableState<Task>,
    isTestPaused: MutableState<Boolean>,
    taskRoomItem: MutableState<TaskRoomItem>
) {
    val currentTask = remember{ task }
    val vocabulary = currentTask.value.vocabulary
    val wordsRemain = remember {currentTask.value.testWordsRemain}
    val currentWord = remember {currentTask.value.currentTestWord}
    val currentTestItem = remember {currentTask.value.currentTestItem}
    val variants = remember { setVariants(currentWord.value.trans, vocabulary) }
    val timerValue = remember { mutableStateOf(7) }
    var localVariants: MutableList<String>
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 10.dp), elevation = 5.dp) {
        val letters = listOf("a)", "b)", "c)", "d)", "e)")
        Column(
            Modifier
                .fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center){
                Button(
                    modifier = Modifier.width(70.dp),
                    onClick = {
                        isTestPaused.value = !isTestPaused.value
                }) {
                    if (!isTestPaused.value)
                        Icon(painterResource(R.drawable.ic_baseline_pause_24), contentDescription = "pause test")
                    else
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "start test")
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center) {
                Card(
                    Modifier
                        .width(70.dp)
                    ,elevation = 5.dp) {
                    Box(modifier = Modifier.padding( 5.dp),
                        contentAlignment = Alignment.Center) {
                        if (!isTestPaused.value) {
                            LaunchedEffect(key1 = timerValue.value , key2 = !isTestPaused.value ) {
                                if (timerValue.value > 0 && !isTestPaused.value) {
                                    delay(1000)
                                    timerValue.value--
                                } else{
                                    currentTask.value.wrongTestAnswers[currentTestItem.value] = "no answer time out"
                                    taskRoomItem.value.wrongTestAnswers[currentTestItem.value] = "no answer time out"
                                    if (wordsRemain.value > 0) {
                                        currentTask.value.currentTestItem.value++
                                        taskRoomItem.value.currentTestItem++
                                        currentTask.value.testRefresh()
                                        localVariants = setVariants(currentWord.value.trans, vocabulary)
                                        if (localVariants.size == variants.size) {
                                            for (i in localVariants.indices) variants[i] =
                                                localVariants[i]
                                        }
                                        timerValue.value = 7
                                    }
                                }
                            }
                        }
                        Text(text = "${timerValue.value}", fontSize = 18.sp)
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(6f),
            contentAlignment = Alignment.Center){
                Column(modifier = Modifier.fillMaxSize().padding(bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    for (i in variants.indices) {
                        Card(
                            Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .fillMaxHeight().weight(1f),
                            elevation = 5.dp
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .clickable {
                                        if (wordsRemain.value > 0) {
                                            if (variants[i] == currentWord.value.trans) {
                                                timerValue.value = 7
                                            } else {
                                                task.value.wrongTestAnswers[currentTestItem.value] = variants[i]
                                                taskRoomItem.value.wrongTestAnswers[currentTestItem.value] = variants[i]
                                                timerValue.value = 7
                                            }
                                            task.value.currentTestItem.value++
                                            taskRoomItem.value.currentTestItem++
                                            task.value.testRefresh()
                                            Log.d("taggg", "task.currentTestItem = ${task.value.currentTestItem.value}")
                                            localVariants =
                                                setVariants(currentWord.value.trans, vocabulary)
                                            if (localVariants.size == variants.size) {
                                                for (j in localVariants.indices) variants[j] =
                                                    localVariants[j]
                                            }
                                        }
                                    }, contentAlignment = Alignment.CenterStart){
                                Text(text = "${letters[i]}  ${variants[i]}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}