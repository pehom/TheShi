package com.pehom.theshi.presentation.screens.mainscreenviews

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.screens.mentorscreen.DrawerAddStudent
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

@Composable
fun DrawerCustom(
                auth: FirebaseAuth,
                viewModel: MainViewModel,
                scaffoldState: ScaffoldState) {
   val drawerState = remember { viewModel.drawerType }
    when (drawerState.value) {
        Constants.DRAWER_ADD_NEW_TASK -> DrawerCreateNewTask(viewModel = viewModel, scaffoldState = scaffoldState )
        Constants.DRAWER_ADD_STUDENT -> DrawerAddStudent(viewModel = viewModel, scaffoldState = scaffoldState)
        Constants.DRAWER_USER_PROFILE -> DrawerUserProfile(viewModel= viewModel, scaffoldState = scaffoldState, auth)
    }
}