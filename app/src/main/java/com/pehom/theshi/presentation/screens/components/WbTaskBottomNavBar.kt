package com.pehom.theshi.presentation.screens.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WbTaskBottomNavBar(
    viewModel: MainViewModel,
    wbTaskScreenState: MutableState<Int>
) {
    val items = listOf(
        TaskBottomNavItem.taskInfo,
        TaskBottomNavItem.learningScreen,
        TaskBottomNavItem.gameScreen,
        TaskBottomNavItem.testScreen
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.Gray
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.iconResource), contentDescription = stringResource(id = item.titleResource)) },
                label = { Text(text = stringResource(id = item.titleResource)) },
                selected = false,
                alwaysShowLabel = true,
                selectedContentColor = Color.DarkGray,
                unselectedContentColor = Color.Gray,
                onClick = {
                    val updateWbTaskRoomItem = viewModel.currentWordbookTaskRoomItem.value
                    updateWbTaskRoomItem.progress = viewModel.currentTask.value.progress
                    updateWbTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
                    updateWbTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
                    updateWbTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
                    updateWbTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.updateTaskRoomItem(updateWbTaskRoomItem){
                            viewModel.viewModelScope.launch(Dispatchers.Main){
                                wbTaskScreenState.value = item.screen
                            }
                        }
                    }
                })
        }
    }
}

sealed class WbTaskBottomNavItem(
    var screen: Int ,
    var iconResource: Int ,
    var titleResource: Int
) {
    object taskInfo: WbTaskBottomNavItem(Constants.MODE_TASK_INFO, R.drawable.ic_baseline_info_24, R.string.task_info)
    object learningScreen: WbTaskBottomNavItem(Constants.MODE_LEARNING_SCREEN, R.drawable.ic_baseline_learning_24, R.string.learning)
    object gameScreen: WbTaskBottomNavItem(Constants.MODE_GAME_SCREEN, R.drawable.ic_round_task_game_mk2_24, R.string.game)
    object testScreen: WbTaskBottomNavItem(Constants.MODE_TEST_SCREEN, R.drawable.ic_baseline_test_mk3_24, R.string.test)
}

