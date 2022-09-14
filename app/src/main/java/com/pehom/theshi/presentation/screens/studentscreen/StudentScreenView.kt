package com.pehom.theshi.presentation.screens.studentscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.presentation.screens.mainscreenviews.TaskListItem
import com.pehom.theshi.utils.Constants

@Composable
fun StudentScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    val tasks = remember {viewModel.tasksInfo}
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp),
        elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)){
                Text(stringResource(id =  R.string.tasks), fontSize = 20.sp,modifier= Modifier.padding(10.dp))
            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(7f)
                .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                itemsIndexed(tasks) {index, item ->
                   TaskListItem(
                       viewModel = viewModel,
                       taskNumber = index,
                       taskProgress =item.progress,
                       title = item.title)
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