package com.pehom.theshi.presentation.screens.studentscreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.screens.components.DialogTasksFilter
import com.pehom.theshi.presentation.screens.components.TaskListItem
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers

@Composable
fun StudentScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val TAG = "StudentScreenView"
    val scope = rememberCoroutineScope()
   // var tasksFs = listOf<TaskRoomItem>()
    val context = LocalContext.current
    val dialogState = remember{ mutableStateOf(false) }
    val filter = remember {viewModel.tasksFilterState}
    val filterText = remember{ mutableStateOf("") }
    when (filter.value){
        Constants.FILTER_ALL -> filterText.value = context.getString(R.string.filter_all)
        Constants.FILTER_IN_PROGRESS -> filterText.value = context.getString(R.string.filter_in_progress)
        Constants.FILTER_FINISHED -> filterText.value = context.getString(R.string.filter_finished)
        Constants.FILTER_CANCELLED -> filterText.value = context.getString(R.string.filter_cancelled)
    }
    var taskRoomItems = listOf<TaskRoomItem>()
    if (viewModel.user.value.fsId.value != "") {
        taskRoomItems = Constants.REPOSITORY.readTaskRoomItemsByUserFsId(viewModel.user.value.fsId.value)
            .observeAsState(listOf()).value
    }
   // val taskRoomItems = Constants.REPOSITORY.readTaskRoomItemsByUserFsId(viewModel.user.value.fsId.value).observeAsState(listOf()).value
    Log.d(TAG, " taskRoomItems = $taskRoomItems")
    DialogTasksFilter(viewModel = viewModel, _dialogState =dialogState )
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp),
        elevation = 5.dp) {
        Column(
            modifier = Modifier
            .fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(

                    ){
                        Text(stringResource(id =  R.string.tasks), fontSize = 20.sp,
                            // modifier= Modifier.padding(10.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.CenterEnd){
                        Row(
                            modifier = Modifier.clickable {
                                dialogState.value = true
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                                contentDescription = "tasks_filter")
                            Text(text = ": ${filterText.value}", modifier = Modifier.padding(end = 15.dp))
                        }
                    }
                }
          //  Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(10f)
                .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                itemsIndexed(taskRoomItems) {_, item ->
                    when(filter.value){
                        Constants.FILTER_ALL -> {
                            TaskListItem(
                                viewModel = viewModel,
                                taskRoomItem = item,
                            )
                        }
                        Constants.FILTER_IN_PROGRESS ->{
                            if (item.status == Constants.STATUS_IN_PROGRESS){
                                TaskListItem(
                                    viewModel = viewModel,
                                    taskRoomItem = item,
                                )
                            }
                        }
                        Constants.FILTER_FINISHED -> {
                            if (item.status == Constants.STATUS_FINISHED){
                                TaskListItem(
                                    viewModel = viewModel,
                                    taskRoomItem = item,
                                )
                            }
                        }
                        Constants.FILTER_CANCELLED -> {
                            if (item.status == Constants.STATUS_CANCELLED){
                                TaskListItem(
                                    viewModel = viewModel,
                                    taskRoomItem = item,
                                )
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.5f)
                .padding(end = 10.dp, bottom = 10.dp), contentAlignment = Alignment.BottomEnd) {
                FloatingActionButton( onClick = {
                    scope.launch {
                        viewModel.drawerType.value = Constants.DRAWER_ADD_NEW_TASK
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.new_task))
                }
            }
        }
    }
}