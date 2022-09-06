package com.pehom.theshi.presentation.screens.mentorscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.testdata.getPendingRequests
import kotlinx.coroutines.launch
import com.pehom.theshi.R

@Composable
fun MentorScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
) {
    val students = viewModel.students
    val scope = rememberCoroutineScope()
    val pendingRequests = getPendingRequests()
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp),
        elevation = 5.dp) {
        Column(modifier = Modifier.fillMaxSize()
        ){
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text( text =  stringResource(id = R.string.your_students), fontSize = 20.sp,modifier= Modifier.padding(10.dp))
                Text( text =  stringResource(id = R.string.pending_Requests) + "  $pendingRequests",
                    fontSize = 16.sp ,modifier= Modifier.padding(10.dp),
                    color = if (pendingRequests != 0)  Color.Green else Color.Transparent)
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(7f)
                .padding(10.dp)
            ) {
                itemsIndexed(students) {index, item ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                        .clickable {
                            viewModel.isStudentProfileShown.value = true
                            viewModel.studentNumber.value = index
                        }
                        ,contentAlignment = Alignment.CenterStart) {
                        Text(text = "${index+1})  ${item.name}   knows ${item.learnedWords} words ", fontSize = 20.sp,)
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
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_student))
                }
            }
        }
    }
}