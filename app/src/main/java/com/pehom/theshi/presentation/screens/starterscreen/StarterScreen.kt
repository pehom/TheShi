package com.pehom.theshi.presentation.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StarterScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    Log.d("ppp", "StarterScreen invoked")

    val words = listOf("Learn", "some", "words", "to", "speak", "something", "like", "this")
    val word = remember { mutableStateOf("")}
    val visible = remember { mutableStateOf(false)}
    val isViewModelSet = remember {viewModel.isViewModelSet}
    val contentType = remember { mutableStateOf(true) }
    LaunchedEffect(key1 = null) {
        words.forEachIndexed(){index, item ->
            word.value = item
            visible.value = true
            delay(330)
            visible.value = false
            delay(150)
            //  Log.d("viewModel", "starter screen auth.currentUser = ${auth.currentUser}")
            Log.d("viewModel", "starter screen index = $index")

            if (index == words.size-1) {
                viewModel.isStarterScreenEnded.value = true
                if (isViewModelSet.value) {
                    viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                    Log.d("viewModel", "starter screen viewModel.screenState = ${viewModel.screenState.value}")
                } else if (isNetworkAvailable()) {
                    contentType.value = false
                } else {
                    viewModel.screenState.value = viewModel.MODE_LOGIN_SCREEN
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        if (contentType.value) {
            JumpinWord(word = word, visible = visible)
        } else {
            CircularProgressIndicator()
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun JumpinWord(word: MutableState<String>, visible: MutableState<Boolean>) {
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = visible.value,
                    Modifier.fillMaxSize(),
                    enter = scaleIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(50)),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = word.value, fontSize =65.sp)
                    }
                }
            }
        }
    }
}




