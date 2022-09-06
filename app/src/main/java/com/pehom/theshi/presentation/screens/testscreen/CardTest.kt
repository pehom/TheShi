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

@Composable
fun CardTest(
    task: MutableState<Task>,
    isStarted: MutableState<Boolean>
) {
    val currentTask = remember{ task }
    val vocabulary = currentTask.value.vocabulary
    val wordsRemain = remember {currentTask.value.testWordsRemain}
    val currentWord = remember {currentTask.value.currentTestWord}
    val currentTestItem = remember {currentTask.value.currentTestItem}
    val variants = remember { setVariants(currentWord.value.translation, vocabulary) }
    val timerValue = remember { mutableStateOf(7) }
    var localVariants: MutableList<String>
    val playButtonState = remember { mutableStateOf(false) }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp), elevation = 5.dp) {
        val letters = listOf("a)", "b)", "c)", "d)", "e)")
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Button( onClick = {
                    task.value.isTestGoing.value = !task.value.isTestGoing.value
                }) {
                    if (!playButtonState.value)
                        Icon(painterResource(R.drawable.ic_baseline_pause_24), contentDescription = "pause test")
                    else
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "start test")
                }
            }
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Card(
                    Modifier
                        .width(100.dp)
                        .padding(start = 15.dp, top = 15.dp, end = 15.dp), elevation = 5.dp) {
                    Box(modifier = Modifier.padding( 5.dp),
                        contentAlignment = Alignment.Center) {
                        if (isStarted.value) {
                            LaunchedEffect(key1 = timerValue.value , key2 = isStarted.value ) {
                                if (timerValue.value > 0 && isStarted.value) {
                                    delay(1000)
                                    timerValue.value--
                                } else{
                                    currentTask.value.wrongTestAnswers[currentTestItem.value] = "no answer time out"
                                    if (wordsRemain.value > 0) {
                                        currentTask.value.currentTestItem.value++
                                        currentTask.value.testRefresh()
                                        localVariants = setVariants(currentWord.value.translation, vocabulary)
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
            for (i in variants.indices) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (wordsRemain.value > 0) {
                                    if (variants[i] == currentWord.value.translation) {
                                        task.value.correctTestAnswers.add(currentWord.value)
                                        timerValue.value = 7
                                    } else {
                                        task.value.wrongTestAnswers[currentTestItem.value] =
                                            variants[i]
                                        timerValue.value = 7
                                    }
                                    task.value.currentTestItem.value++
                                    task.value.testRefresh()
                                    Log.d(
                                        "taggg",
                                        "task.correctAnswers = ${task.value.correctTestAnswers}"
                                    )
                                    Log.d(
                                        "taggg",
                                        "task.currentTestItem = ${task.value.currentTestItem.value}"
                                    )
                                    localVariants =
                                        setVariants(currentWord.value.translation, vocabulary)
                                    if (localVariants.size == variants.size) {
                                        for (j in localVariants.indices) variants[j] =
                                            localVariants[j]
                                    }
                                }
                            }, contentAlignment = Alignment.CenterStart){
                        Text(text = "${letters[i]}  ${variants[i]}", fontSize = 22.sp)
                    }
                }
            }
        }
    }
}