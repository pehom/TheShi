package com.pehom.theshi.presentation.screens.mentorscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.screens.components.*
import com.pehom.theshi.presentation.screens.studentprofilescreen.StudentProfileView
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MentorScreen(
    viewModel: MainViewModel,
    auth: FirebaseAuth)
{
    Log.d("ppp", "MentorScreen is on")

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
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f)) {
                Header(viewModel, scaffoldState)
            }
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f)) {
                SwitchMode(viewModel)
            }

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(7f)) {
                if (viewModel.isStudentProfileShown.value)
                    StudentProfileView(viewModel = viewModel, scaffoldState = scaffoldState)
                else
                    MentorScreenView(viewModel = viewModel, scaffoldState = scaffoldState)
            }
        }
    }
}