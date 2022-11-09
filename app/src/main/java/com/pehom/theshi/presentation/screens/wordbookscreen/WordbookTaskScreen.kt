package com.pehom.theshi.presentation.screens.wordbookscreen

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.presentation.screens.components.WbTaskBottomNavBar
import com.pehom.theshi.presentation.screens.gamescreen.GameScreen
import com.pehom.theshi.presentation.screens.infowordbooktaskscreen.InfoWordbookTaskScreen
import com.pehom.theshi.presentation.screens.learningscreen.LearningScreen
import com.pehom.theshi.presentation.screens.testscreen.TestScreen
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun WordbookTaskScreen(
    viewModel: MainViewModel,
    tts: TextToSpeech?
){
    val wbTaskScreenState = remember { mutableStateOf(Constants.MODE_TASK_INFO) }
    val isTestScreenActive = remember { mutableStateOf(false) }
    val isTestPaused = remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = { WbTaskBottomNavBar(viewModel = viewModel, wbTaskScreenState) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (wbTaskScreenState.value) {
                    Constants.MODE_TASK_INFO -> {
                        isTestScreenActive.value = false
                        //   isTestStarted.value = false
                        InfoWordbookTaskScreen(viewModel = viewModel)
                    }
                    Constants.MODE_LEARNING_SCREEN -> {
                        isTestScreenActive.value = false
                        //  isTestStarted.value = false
                        LearningScreen(viewModel = viewModel, viewModel.currentWordbookTaskRoomItem, tts)
                    }
                    Constants.MODE_GAME_SCREEN -> {
                        isTestScreenActive.value = false
                        //  isTestStarted.value = false
                        GameScreen(viewModel = viewModel,viewModel.currentWordbookTaskRoomItem, tts)
                    }
                    Constants.MODE_TEST_SCREEN -> {
                        isTestScreenActive.value = true
                        //  isTestStarted.value = false
                        TestScreen(viewModel = viewModel, isTestScreenActive,  isTestPaused,viewModel.currentWordbookTaskRoomItem)
                    }
                }
            }
        },
        backgroundColor = Color.White
    )
}