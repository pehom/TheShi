package com.pehom.theshi.presentation.screens.learningscreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary

@Composable
fun CardLearning(task: MutableState<Task>,
    _isRestarted: MutableState<Boolean>
                 ) {
    val WRONG_ANSWER = 1
    val CORRECT_ANSWER = 2
    val NO_ANSWER = 3
    val currentTask = remember { task }
    val vocabulary = currentTask.value.vocabulary
    val wordsRemain = remember {currentTask.value.learningWordsRemain}
    val currentWord = remember {currentTask.value.currentLearningWord}
    val currentLearningItem = remember {currentTask.value.currentLearningItem}
    val variants = remember { setLearningVariants(currentWord.value.orig, vocabulary) }
    var localVariants = mutableListOf<String>()
    val answerState = remember { mutableStateOf(NO_ANSWER) }
    val letters = listOf("a)", "b)", "c)", "d)", "e)")
    val selectedIndex = remember { mutableStateOf(-1) }
    val isRestarted = remember {_isRestarted}
    Column(
        Modifier.fillMaxSize()
        ,
        verticalArrangement = Arrangement.SpaceAround) {
        if (isRestarted.value) {
            task.value.currentLearningItem.value = 0
            task.value.learningRefresh()
            answerState.value = NO_ANSWER
            localVariants = setLearningVariants(currentWord.value.orig, vocabulary)
            if (localVariants.size == variants.size) {
                for (j in localVariants.indices) variants[j] =
                    localVariants[j]
            }
            _isRestarted.value = false
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            // .padding(horizontal = 10.dp)
            .weight(5f),
            contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in variants.indices) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .border(
                                width = 2.dp,
                                color = if (selectedIndex.value == i) {
                                    when (answerState.value) {
                                        WRONG_ANSWER -> Color.Red
                                        NO_ANSWER -> Color.Transparent
                                        else -> Color.Green
                                    }
                                } else {
                                    Color.Transparent
                                },
                                RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                if (wordsRemain.value > 0) {
                                    selectedIndex.value = i
                                    if (variants[i] == currentWord.value.orig) {
                                        answerState.value = CORRECT_ANSWER
                                    } else {
                                        answerState.value = WRONG_ANSWER
                                    }
                                }
                            },
                        elevation = 5.dp,

                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                            , contentAlignment = Alignment.CenterStart){
                            Text(text = "${letters[i]}  ${variants[i]}", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                onClick = {
                answerState.value = NO_ANSWER
                if (currentLearningItem.value > 0) {
                    task.value.currentLearningItem.value--
                    task.value.learningRefresh()
                    localVariants = setLearningVariants(currentWord.value.orig, vocabulary)
                    if (localVariants.size == variants.size) {
                        for (j in localVariants.indices) variants[j] =
                            localVariants[j]
                    }
                }
            }) {
                Icon(painterResource(id = R.drawable.ic_baseline_west_24), contentDescription = "previous" )
                //Text(stringResource(id = R.string.previous))
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                onClick = {
                answerState.value = NO_ANSWER
                if (currentLearningItem.value < vocabulary.items.size -1 ) {
                    task.value.currentLearningItem.value++
                    task.value.learningRefresh()
                    localVariants = setLearningVariants(currentWord.value.orig, vocabulary)
                    if (localVariants.size == variants.size) {
                        for (j in localVariants.indices) variants[j] =
                            localVariants[j]
                    }
                } else if (currentLearningItem.value == vocabulary.items.size -1){
                    task.value.currentLearningItem.value++
                    task.value.learningRefresh()
                    variants.clear()
                    variants+= mutableListOf("","","","","",)
                }
            }) {
                Icon(painterResource(id = R.drawable.ic_baseline_east_24), contentDescription = "next" )
               // Text(stringResource(id = R.string.next))
            }
        }
    }
}

private fun setLearningVariants(
    currentWord: String,
    vocabulary: Vocabulary
):MutableList<String> {
    val resultSet = mutableSetOf(currentWord)
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