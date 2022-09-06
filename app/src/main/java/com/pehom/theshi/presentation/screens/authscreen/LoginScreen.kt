package com.pehom.theshi.presentation.screens.loginscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R

@Composable
fun LoginScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = {
            //  TODO() signIn()
            viewModel.screenState.value = viewModel.MODE_SIGN_IN_SCREEN
        }) {
            Text(text = stringResource(id = R.string.sign_in))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            //  authViewModel.screenMode = authViewModel.REGISTER_SCREEN
            viewModel.screenState.value = viewModel.MODE_REGISTER_SCREEN
            //TODO   register()
        }) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}