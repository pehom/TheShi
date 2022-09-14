package com.pehom.theshi.presentation.screens.mainscreenviews

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerUserProfile(viewModel: MainViewModel, scaffoldState: ScaffoldState, auth: FirebaseAuth) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Column(
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
      /* OutlinedTextField(
           modifier = Modifier
               .fillMaxWidth()
               .padding(10.dp),
           value = newTaskTitle.value,
           onValueChange = {newTaskTitle.value = it},
           label = { Text(text = stringResource(id = R.string.new_task_title)) },
           placeholder = { Text(stringResource(id = R.string.new_task_title)) },
           keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
           keyboardActions = KeyboardActions(
               onDone = {
                   scope.launch {
                       keyboardController?.hide()
                       focusManager.clearFocus()
                   }
               })
       )*/
       Button(onClick = {
           auth.signOut()
           viewModel.screenState.value = viewModel.MODE_LOGIN_SCREEN
       }) {
           Text(text = stringResource(id = R.string.sign_out), fontSize = 20.sp)
       }
       Button(onClick = {
           Log.d("userInfo", "vm.user fsID = ${viewModel.user.fsId} ")
           Log.d("userInfo", "vm.user phoneNumber = ${viewModel.user.phoneNumber} ")
           Log.d("userInfo", "vm.user email = ${viewModel.user.email} ")
           Log.d("userInfo", "vm.user funds = ${viewModel.user.funds.amount()} ")
           viewModel.useCases.readFirestoreUserInfoUseCase.execute(viewModel.user.fsId, context) {}
       }) {
           Text(text = "user info")
       }

   }

}

