package com.pehom.theshi.presentation.screens.mentorscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask
import com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCustom
import com.pehom.theshi.presentation.screens.mainscreenviews.Header
import com.pehom.theshi.presentation.screens.mainscreenviews.SwitchMode
import com.pehom.theshi.presentation.screens.studentprofilescreen.StudentProfileView
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun MentorScreen(viewModel: MainViewModel, auth: FirebaseAuth) {

    // val students = viewModel.students
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = {
          DrawerCustom(
            viewModel = viewModel,
            scaffoldState = scaffoldState,
            auth = auth)
        },
        drawerShape = MaterialTheme.shapes.large
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Header(viewModel.drawerType, scaffoldState)
            SwitchMode(viewModel)
            if (viewModel.isStudentProfileShown.value)
                StudentProfileView(viewModel = viewModel, scaffoldState = scaffoldState)
            else
                MentorScreenView(viewModel = viewModel, scaffoldState = scaffoldState)
        }
    }
}