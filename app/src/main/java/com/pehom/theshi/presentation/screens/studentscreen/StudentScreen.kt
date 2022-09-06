package com.pehom.theshi.presentation.screens.studentscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask
import com.pehom.theshi.presentation.screens.mainscreenviews.Header
import com.pehom.theshi.presentation.screens.mainscreenviews.SwitchMode
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.testdata.getTasks

@Composable
fun StudentScreen(viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    val userIsStudentMock = mutableListOf(Student("+79201112222", "Senya"))
    userIsStudentMock[0].tasks = getTasks()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerCreateNewTask(
                viewModel,
                scaffoldState,
            )
        },
        drawerShape = MaterialTheme.shapes.large
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Header()
            SwitchMode(viewModel)
            StudentScreenView(viewModel, scaffoldState)
        }
    }
}