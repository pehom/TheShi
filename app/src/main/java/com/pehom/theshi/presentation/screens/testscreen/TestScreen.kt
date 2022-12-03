package com.pehom.theshi.presentation.screens.testscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun TestScreen(
    viewModel: MainViewModel,
    isTestScreenActive: MutableState<Boolean>,
    isTestPaused: MutableState<Boolean>,
    taskRoomItem: MutableState<TaskRoomItem>
    ) {
    Log.d("ppp", "TestScreen is on")

    val currentTask = remember { mutableStateOf(viewModel.currentTask.value) }
    val wordsRemain = remember {currentTask.value.testWordsRemain}
    val currentWordDisplay = remember { currentTask.value.currentTestWordDisplay }
    val isWrongAnswersShown = remember { mutableStateOf(false) }
    if (taskRoomItem.value.currentTestItem == 0) {
        taskRoomItem.value.wrongTestAnswers.clear()
    }
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
                .padding(start = 15.dp, top = 10.dp, end = 15.dp),
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
                            viewModel.currentTask.value.currentTestItem.value = 0
                            viewModel.currentTask.value.isTestGoing.value = false
                            viewModel.currentTask.value.wrongTestAnswers.clear()
                            viewModel.currentTask.value.testRefresh()

                        }) {
                            Icon(painterResource(id = R.drawable.ic_baseline_restart_alt_24), contentDescription = "retry")
                          //  Text(text = stringResource(id = R.string.retry), fontSize = 12.sp)
                        }
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
            Box(modifier = Modifier.padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center) {
                Text(text = if (!isTestPaused.value) currentWordDisplay.value
                            else stringResource(id = R.string.test_must_go_on),
                    fontSize = 22.sp)
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(5f)
            ){
            if ( isTestScreenActive.value && !isTestPaused.value) {
                if (wordsRemain.value > 0) {
                    CardTest(currentTask, isTestPaused, taskRoomItem, currentWordDisplay)
                } else if (!isWrongAnswersShown.value){
                    CardTestResult(currentTask,viewModel, isWrongAnswersShown, taskRoomItem, isTestPaused)
                } else {
                    CardWrongAnswers(currentTask,viewModel, isWrongAnswersShown, taskRoomItem)
                }
            } else {
                CardStart(currentTask, /*isTestStarted,*/ isTestPaused)
            }
        }

    }
}

fun setVariants(
    currentWord: MutableState<VocabularyItemScheme>,
    vocabulary: Vocabulary,
    currentWordDisplay: MutableState<String>
): MutableList<String> {
    val random = (0..1).random()
    Log.d(Constants.INSPECTING_TAG, "random = $random")
    if (random == 0){
        val resultSet = mutableSetOf(currentWord.value.trans)
        currentWordDisplay.value = currentWord.value.orig
        val words = mutableListOf<String>()
        for (word in vocabulary.items) words.add(word.trans)
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
    } else {
        val resultSet = mutableSetOf(currentWord.value.orig)
        currentWordDisplay.value = currentWord.value.trans
        val words = mutableListOf<String>()
        for (word in vocabulary.items) words.add(word.orig)
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

}