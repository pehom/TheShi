package com.pehom.theshi.presentation.screens.studentscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.screens.components.DrawerCustom
import com.pehom.theshi.presentation.screens.components.Header
import com.pehom.theshi.presentation.screens.components.SwitchMode
import com.pehom.theshi.presentation.screens.components.WordbookCard
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun StudentScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    Log.d("ppp", "StudentScreen is on")

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
                .padding(bottom = it.calculateBottomPadding())
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f)) {
                Header(viewModel, scaffoldState)
            }
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f)) {
                SwitchMode(viewModel)
            }
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f)) {
                WordbookCard(viewModel)
            }
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(7f)) {
                StudentScreenView(viewModel, scaffoldState)
            }
        }
    }
}