package com.pehom.theshi.presentation.screens.mainscreenviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun TaskListItem( viewModel:MainViewModel, taskNumber: Int, taskProgress: Int, title: String) {
    Box(
        modifier = Modifier.fillMaxSize()
            .border(width = 5.dp, shape = RoundedCornerShape(4.dp),
                color = if(taskNumber==0) Color.Yellow else Color.Transparent)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(
                Brush.horizontalGradient(
                    (taskProgress.toFloat() / 100) to Color.Green,
                    (0.05f + taskProgress.toFloat() / 100) to Color.Red,
                    startX = 0.1f
                ), RoundedCornerShape(4.dp)
            )
            .clickable {
                viewModel.currentTaskNumber.value = taskNumber
                if (viewModel.lastTaskInfo.value.id == viewModel.tasksInfo[taskNumber].id)
                    viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
                else
                    viewModel.useCases.setTaskByTitle.execute(viewModel)

            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text ="$title  ", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
            Text(text = ".. ${taskProgress}%", fontSize = 16.sp, modifier = Modifier.padding(end = 10.dp))   //(1-taskProgress.value.toFloat()/100)  (taskProgress.value.toFloat() / 100)
        }
    }
}