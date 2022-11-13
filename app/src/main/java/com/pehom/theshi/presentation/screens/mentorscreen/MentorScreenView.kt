package com.pehom.theshi.presentation.screens.mentorscreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.utils.Constants

@Composable
fun MentorScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
) {
    /*val students = Constants.REPOSITORY
        .readStudentRoomItemsByMentorId(viewModel.user.value.fsId.value).observeAsState(listOf()).value*/
    val students = remember { mutableStateListOf(Student(FsId(""),"","")) }
    if (viewModel.user.value.fsId.value != "") {
        viewModel.useCases.readStudentsFsUseCase.execute(viewModel){
            students.clear()
            students.addAll(it)
        }
    }

    val scope = rememberCoroutineScope()
   // val pendingRequests = remember { viewModel.addingRequests}
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
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(7f)
                .padding(10.dp)
            ) {
                itemsIndexed(students) { _, item ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                        .clickable {
                            viewModel.currentStudent.value = item
                            // viewModel.studentFsId.value = item.fsId
                            viewModel.isStudentProfileShown.value = true
                            // viewModel.studentNumber.value = index
                        }
                        ,contentAlignment = Alignment.CenterStart) {
                        Text(text = item.name, fontSize = 20.sp,)
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.5f)
                .padding(end = 10.dp, bottom = 10.dp), contentAlignment = Alignment.BottomEnd) {
                FloatingActionButton( onClick = {
                    Log.d("mentorScreenView FAB pressed", "viewModel.isStudentProfileShown = ${viewModel.isStudentProfileShown.value}")
                    if (viewModel.isStudentProfileShown.value)
                        viewModel.drawerType.value = Constants.DRAWER_ADD_NEW_TASK
                    else
                        viewModel.drawerType.value = Constants.DRAWER_ADD_STUDENT
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