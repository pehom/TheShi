package com.pehom.theshi.presentation.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StarterScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    Log.d("ppp", "StarterScreen is on")

    val words = listOf("Learn", "some", "words", "to", "speak", "something", "like", "this")
    val word = remember { mutableStateOf("")}
    val visible = remember { mutableStateOf(false)}

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

    LaunchedEffect(key1 = null/*viewModel.isStarterScreenEnded.value*/) {
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
                if (auth.currentUser != null) {
                    Log.d("viewModel", "starter screen viewModel.isViewModelSet.value = ${viewModel.isViewModelSet.value}")
                    if (viewModel.isViewModelSet.value) {
                        viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                        Log.d("viewModel", "starter screen viewModel.screenState = ${viewModel.screenState.value}")
                    }
                } else {
                    viewModel.screenState.value = viewModel.MODE_LOGIN_SCREEN
                    Log.d("viewModel", "starter screen viewModel.screenState = ${viewModel.screenState.value}")
                }
            }
        }
    }
}




