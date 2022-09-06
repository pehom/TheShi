package com.pehom.theshi.presentation.screens.testscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Vocabulary

@Composable
fun TestScreen(viewModel: MainViewModel) {
    Log.d("xxx", "vm.tasks[vm.currentTaskNumber.value].currentTestItem = ${viewModel.tasks[viewModel.currentTaskNumber.value].currentTestItem.value}")
    val wordsRemain = remember {viewModel.tasks[viewModel.currentTaskNumber.value].testWordsRemain }
    Log.d("xxx", "test screen: wordsRemain = ${wordsRemain.value}")

    val currentTask = remember { mutableStateOf(viewModel.tasks[viewModel.currentTaskNumber.value]) }
    val currentWordDisplay = remember { currentTask.value.currentTestWordDisplay }
    val isWrongAnswersShown = remember { mutableStateOf(false) }
    val isStarted = remember { currentTask.value.isTestGoing }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.13f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            elevation = 5.dp) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.padding( 5.dp),
                    contentAlignment = Alignment.Center) {
                    Text(text = currentTask.value.title)
                }
                Box(modifier = Modifier.padding(5.dp),
                    contentAlignment = Alignment.Center){
                    Text(text = stringResource(id = R.string.words_remain) + "   ${wordsRemain.value}")
                }
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            elevation = 5.dp) {
            Box(modifier = Modifier.padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center) {
                Text(text = currentWordDisplay.value,
                    fontSize = 22.sp)
            }
        }
        if (isStarted.value) {
            Log.d("ttt", "currentTask.wordsRemain ${currentTask.value.testWordsRemain.value}")
            Log.d("ttt", "currentTask.currentTestItem ${currentTask.value.currentTestItem.value}")
            Log.d("ttt", "currentTask.currentWordDisplay ${currentTask.value.currentTestWordDisplay.value}")

            if (wordsRemain.value > 0) {
                CardTest(currentTask, isStarted)
            } else if (!isWrongAnswersShown.value){
                CardTestResult(currentTask,viewModel, isWrongAnswersShown)
            } else {
                CardWrongAnswers(currentTask,viewModel, isWrongAnswersShown)
            }
        } else {
            CardStart(currentTask, isStarted)
        }
    }
}

fun setVariants(currentWord: String, vocabulary: Vocabulary): MutableList<String> {
    val resultSet = mutableSetOf(currentWord)
    val words = mutableListOf<String>()
    for (word in vocabulary.items) words.add(word.translation)
    Log.d("setVariants", "words = $words")
    if (words.size > 4) {
        while (resultSet.size < 5) {
            resultSet.add(words.random())
        }
    } else {
        resultSet.add("variant1")
        resultSet.add("variant2")
        resultSet.add("variant3")
        resultSet.add("variant4")
        // val shuffledList = resultSet.asSequence().shuffled().toList()
    }
    val r = resultSet.asSequence().shuffled().toList()
    val result = mutableStateListOf("","","","","")
    if (r.size == result.size){
        for (i in r.indices) result[i] = r[i]
    }
    Log.d("setVariants", "resultSet = $r")
    return result
}