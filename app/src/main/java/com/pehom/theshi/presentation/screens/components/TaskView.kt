package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TaskView(
    userFsID: String,
    taskRoomItem: TaskRoomItem
    ) {
    val context = LocalContext.current
    val TAG = "TaskView"
    val taskProgress = taskRoomItem.progress
    val mentorName = remember{ mutableStateOf("")}
    Log.d(TAG, "taskRoomItem = $taskRoomItem")
    if (taskRoomItem.mentorFsId != userFsID) {
        LaunchedEffect(key1 = null ) {
            Constants.REPOSITORY.readMentorRoomItemByMentorFsId(taskRoomItem.mentorFsId){
                Log.d(TAG, "mentorRoomItem = ${it}" )
                if (it != null) {
                    mentorName.value = it.name
                }
            }
        }
    } else {
        mentorName.value = context.getString(R.string.you)
    }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .background(
                    Brush.horizontalGradient(
                        (taskProgress.toFloat() / 100) to Color.Green,
                        (0.05f + taskProgress.toFloat() / 100) to Color.Red,
                        startX = 0.1f
                    ),
                    RoundedCornerShape(5.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(9f),
                contentAlignment = Alignment.CenterStart){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 3.dp), contentAlignment = Alignment.CenterStart){
                        Text(text = stringResource(id = R.string.title) +": ${taskRoomItem.taskTitle}  ")
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
                        Text(text = stringResource(id = R.string.mentor) + ": ${mentorName.value}")
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 3.dp), contentAlignment = Alignment.CenterStart){
                        Text(text = stringResource(id = R.string.status) + " ${taskRoomItem.status}")
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.5f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterEnd) {
                Text(text = ".. ${taskProgress}%", Modifier.padding(end = 10.dp))
            }
        }
}