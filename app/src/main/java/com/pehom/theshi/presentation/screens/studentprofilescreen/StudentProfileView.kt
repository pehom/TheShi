package com.pehom.theshi.presentation.screens.studentprofilescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.TaskInfo
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun StudentProfileView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var student = viewModel.currentStudent.value
    val tasks = remember { viewModel.studentTasks }
    val wordbook = remember { viewModel.studentWordbook }

    if (viewModel.lastStudent.value.fsId.value != viewModel.currentStudent.value.fsId.value) {
        viewModel.useCases.readStudentTasksFsUseCase.execute(context, viewModel){_tasks ->
            viewModel.studentTasks.clear()
            Log.d("Student", "_task = $_tasks")
            viewModel.studentTasks += _tasks
            _tasks.forEach(){
                Log.d("Student", "task = ${it.title}")
            }
        }
        viewModel.useCases.readStudentWordbookFsUseCase.execute(student.fsId.value){_wordbook ->
            viewModel.studentWordbook.clear()
            viewModel.studentWordbook.addAll(_wordbook)
        }
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
       // .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 10.dp),
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.CenterStart) {
                Text(text = stringResource(id = R.string.student) + ": ${student.name}" )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight().weight(10f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 10.dp, end = 5.dp, bottom = 10.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            stringResource(id = R.string.wordbook) + ":",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    LazyColumn(){
                        itemsIndexed(wordbook){_,item ->
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                                Text(text = item)
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 5.dp, end = 10.dp, bottom = 10.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(0.7f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            stringResource(id = R.string.tasks),
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(8f)
                    ){
                        itemsIndexed(tasks) { _, item ->
                            if (item.title != "") {
                                StudentTasksItem(item = item, viewModel)
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1.5f)
                            .padding(end = 10.dp, bottom = 10.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        FloatingActionButton(
                            onClick = {
                                viewModel.drawerType.value = Constants.DRAWER_ADD_NEW_TASK
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.new_task)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentTasksItem(
    item: TaskInfo,
    viewModel: MainViewModel
    ){
    val taskProgress = item.progress
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
           // .background(Color.Transparent, RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp) ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_cancel_mk2_24),
            contentDescription = "delete task",
            modifier = Modifier.clickable {
                viewModel.useCases.deleteStudentTaskByIdFsUseCase
                    .execute(viewModel.currentStudent.value.fsId.value, item.id)
                viewModel.studentTasks.remove(item)
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        (taskProgress.toFloat() / 100) to Color.Green,
                        (0.05f + taskProgress.toFloat() / 100) to Color.Red,
                        startX = 0.1f
                    )
                    , RoundedCornerShape(4.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.title,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = ".. ${taskProgress}%",
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}