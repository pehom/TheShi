package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun TaskListItem(
    viewModel:MainViewModel,
    taskRoomItem: TaskRoomItem,
    ) {
    val TAG = "TaskListItem"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (taskRoomItem.status == Constants.STATUS_IN_PROGRESS) {
                    Log.d(TAG, "currentTaskRoomItem = $taskRoomItem")
                    viewModel.currentTaskRoomItem.value = taskRoomItem
                    viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
                }
            }
    ) {
        TaskView(
            userFsID = viewModel.user.value.fsId.value,
            taskRoomItem = taskRoomItem)
    }
}