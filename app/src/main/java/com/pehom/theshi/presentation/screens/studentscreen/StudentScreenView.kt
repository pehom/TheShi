package com.pehom.theshi.presentation.screens.studentscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R

@Composable
fun StudentScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    val tasks = remember {viewModel.tasks}
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
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                itemsIndexed(tasks) {index, item ->
                    val taskProgress = item.progress
                    Log.d("ggg", "task[$index].progress = $taskProgress")
                    //  Log.d("ggg", "taskNumber = $taskNumber")
                    Row(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                (taskProgress.toFloat() / 100) to Color.Green,
                                (0.05f + taskProgress.toFloat() / 100) to Color.Red,
                                startX = 0.1f
                            ), RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            viewModel.currentTaskNumber.value = index
                            viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text =item.title, fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
                        Text(text = ".. ${taskProgress}%", fontSize = 16.sp, modifier = Modifier.padding(end = 10.dp))   //(1-taskProgress.value.toFloat()/100)  (taskProgress.value.toFloat() / 100)
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
                        //testFireStore()
                        //viewModel.useCases.getAllVocabularyTitles
                        scaffoldState.drawerState.open()
                     //   Log.d("suka", "vm.allVocaTits.size = ${viewModel.allVocabularyTitles?.size}")

                    }
                }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.new_task))
                }
            }
        }
    }
}