package com.pehom.theshi.presentation.screens.components

import android.widget.Toast
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun TaskBottomNavBar(
    viewModel: MainViewModel,
    taskScreenState: MutableState<Int>,
   // isTestStarted: MutableState<Boolean>
) {
    val items = listOf(
        TaskBottomNavItem.taskInfo,
        TaskBottomNavItem.learningScreen,
        TaskBottomNavItem.gameScreen,
        TaskBottomNavItem.testScreen
    )
    val context = LocalContext.current
    val dialogState = remember { mutableStateOf(false) }
    val confirmChangeScreen = remember { mutableStateOf(false) }
    val navItemState = remember { mutableStateOf(TaskBottomNavItem()) }
    DialogConfirmTestRestart(viewModel, dialogState, taskScreenState, navItemState )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.Gray
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {Icon(painterResource(id = item.iconResource), contentDescription = stringResource(id = item.titleResource))},
                label = { Text(text = stringResource(id = item.titleResource))},
                selected = false,
                alwaysShowLabel = true,
                selectedContentColor = Color.DarkGray,
                unselectedContentColor = Color.Gray,
                onClick = {
                    //TODO implement scaffold content
                    if (viewModel.currentTaskRoomItem.value.isAvailable) {
                        if (item.screen == Constants.MODE_GAME_SCREEN || item.screen == Constants.MODE_LEARNING_SCREEN){
                            if (viewModel.currentTaskRoomItem.value.currentTestItem > 0) {
                                navItemState.value = item
                                dialogState.value = true
                            } else {
                                updateCurrentTaskRoomItem(viewModel,taskScreenState,item)
                            }
                        } else {
                            updateCurrentTaskRoomItem(viewModel,taskScreenState,item)
                        }
                    } else{
                        Toast.makeText(context, context.getString(R.string.purchase_vocabulary), Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}

 open class TaskBottomNavItem(
    var screen: Int = -1,
    var iconResource: Int = -1,
    var titleResource: Int = -1
) {
    object taskInfo: TaskBottomNavItem(Constants.MODE_TASK_INFO, R.drawable.ic_baseline_info_24, R.string.task_info)
    object learningScreen: TaskBottomNavItem(Constants.MODE_LEARNING_SCREEN, R.drawable.ic_baseline_learning_24, R.string.learning)
    object gameScreen: TaskBottomNavItem(Constants.MODE_GAME_SCREEN, R.drawable.ic_round_task_game_mk2_24, R.string.game)
    object testScreen: TaskBottomNavItem(Constants.MODE_TEST_SCREEN, R.drawable.ic_baseline_test_mk3_24, R.string.test)
}

private fun updateCurrentTaskRoomItem(
    viewModel: MainViewModel,
    taskScreenState: MutableState<Int>,
    item: TaskBottomNavItem
){
    val updateTaskRoomItem = viewModel.currentTaskRoomItem.value
    updateTaskRoomItem.progress = viewModel.currentTask.value.progress
    updateTaskRoomItem.currentLearningItem = viewModel.currentTask.value.currentLearningItem.value
    updateTaskRoomItem.currentTaskItem = viewModel.currentTask.value.currentTaskItem.value
    updateTaskRoomItem.currentTestItem = viewModel.currentTask.value.currentTestItem.value
    updateTaskRoomItem.wrongTestAnswers = viewModel.currentTask.value.wrongTestAnswers
    viewModel.viewModelScope.launch(Dispatchers.IO) {
        Constants.REPOSITORY.updateTaskRoomItem(updateTaskRoomItem){
            viewModel.viewModelScope.launch(Dispatchers.Main){
                taskScreenState.value = item.screen
            }
        }
    }
}