package com.pehom.theshi.presentation.screens.taskscreen

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pehom.theshi.presentation.screens.components.TaskBottomNavBar
import com.pehom.theshi.presentation.screens.gamescreen.GameScreen
import com.pehom.theshi.presentation.screens.inforegulartaskscreen.InfoRegularTaskScreen
import com.pehom.theshi.presentation.screens.learningscreen.LearningScreen
import com.pehom.theshi.presentation.screens.testscreen.TestScreen
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun TaskScreen(
    viewModel: MainViewModel,
    tts: TextToSpeech?
) {
    Log.d("ppp", "TaskScreen is on")

    val taskScreenState = remember { mutableStateOf(Constants.MODE_TASK_INFO) }
    val isTestScreenActive = remember { mutableStateOf(false) }
  //  val isTestStarted = remember { mutableStateOf(false) }
    val isTestPaused = remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = { TaskBottomNavBar(viewModel = viewModel, taskScreenState/*, isTestStarted*/) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (taskScreenState.value) {
                    Constants.MODE_TASK_INFO -> {
                        isTestScreenActive.value = false
                     //   isTestStarted.value = false
                        InfoRegularTaskScreen(viewModel = viewModel, viewModel.currentTaskRoomItem)
                    }
                    Constants.MODE_LEARNING_SCREEN -> {
                        isTestScreenActive.value = false
                      //  isTestStarted.value = false
                        LearningScreen(viewModel = viewModel, viewModel.currentTaskRoomItem, tts)
                    }
                    Constants.MODE_GAME_SCREEN -> {
                        isTestScreenActive.value = false
                      //  isTestStarted.value = false
                        GameScreen(viewModel = viewModel, viewModel.currentTaskRoomItem, tts)
                    }
                    Constants.MODE_TEST_SCREEN -> {
                        isTestScreenActive.value = true
                      //  isTestStarted.value = false
                        TestScreen(viewModel = viewModel, isTestScreenActive, isTestPaused, viewModel.currentTaskRoomItem)
                    }
                }
            }
        },
      //  backgroundColor = Color.White
    )
}