package com.pehom.theshi.presentation.screens.studentprofilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.StudentRoomItem
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers

@Composable
fun StudentProfileView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val studentRoomItem = viewModel.currentStudent.value
    var student = Student(FsId(studentRoomItem.fsId), studentRoomItem.name)
    val tasks = remember {student.tasks}

    viewModel.useCases.readStudentTasksFsUseCase.execute(context, viewModel){_tasks ->
        tasks.clear()
        tasks += _tasks
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp),
        elevation = 5.dp) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = studentRoomItem.name, fontSize = 22.sp)
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                elevation = 5.dp){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        stringResource(id = R.string.wordbook),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Text(
                        stringResource(id = R.string.tasks),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(7f)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    itemsIndexed(tasks) { index, item ->
                        val taskProgress = item.progress
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        (taskProgress.toFloat() / 100) to Color.Green,
                                        (0.05f + taskProgress.toFloat() / 100) to Color.Red,
                                        startX = 0.1f
                                    ), RoundedCornerShape(4.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                text = ".. ${taskProgress}%",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = 10.dp)
                            )   //(1-taskProgress.value.toFloat()/100)  (taskProgress.value.toFloat() / 100)
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