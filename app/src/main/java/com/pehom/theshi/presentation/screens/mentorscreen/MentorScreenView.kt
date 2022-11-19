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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.StudentRoomItem
import com.pehom.theshi.utils.Constants

@Composable
fun MentorScreenView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
) {
    var students = listOf<StudentRoomItem>()
    if (viewModel.user.value.fsId.value != "") {
        students = Constants.REPOSITORY.readStudentRoomItemsByMentorId(viewModel.user.value.fsId.value).observeAsState(
            listOf()).value
    }
    val scope = rememberCoroutineScope()
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
                .weight(0.7f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text( text =  stringResource(id = R.string.your_students), modifier= Modifier.padding(horizontal = 10.dp))
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(10f)
            ) {
                itemsIndexed(students) { _, item ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .clickable {
                            viewModel.currentStudent.value = item.mapToStudent()
                            // viewModel.studentFsId.value = item.fsId
                            viewModel.isStudentProfileShown.value = true
                            viewModel.drawerType.value = Constants.DRAWER_ADD_NEW_TASK
                            // viewModel.studentNumber.value = index
                        }
                        ,contentAlignment = Alignment.CenterStart) {
                        Text(text = item.name)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
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