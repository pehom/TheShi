package com.pehom.theshi.presentation.screens.authscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.UserRoomItem
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(viewModel: MainViewModel, auth: FirebaseAuth){
    Log.d("ppp", "SignInScreen is on")

    val email = remember {mutableStateOf("")}
    val scope = rememberCoroutineScope()
    val password = remember { mutableStateOf("") }
    val kc = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = email.value,
            onValueChange = {email.value = it},
            label = { Text(text = stringResource(id = R.string.enter_your_email)) },
            placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        kc?.hide()
                        focusManager.clearFocus()
                    }
                })
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = password.value,
            onValueChange = {password.value = it},
            label = { Text(text = stringResource(id = R.string.enter_your_password)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        kc?.hide()
                        focusManager.clearFocus()
                    }
                })
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            onClick = {

                viewModel.useCases.signInFsUseCase.execute(
                    context,
                    viewModel,
                    auth,
                    LoginModel(email.value, password.value)
                ){
                    if (it) {
                        auth.currentUser?.let {fbUser ->
                            viewModel.useCases.setupMainViewModelFsUseCase.execute(context, viewModel, fbUser){
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    var userRoomItem = Constants.REPOSITORY.readUserRoomItemByUserFsId(viewModel.user.value.fsId.value)
                                    if (userRoomItem == null) {
                                        userRoomItem = UserRoomItem(
                                            viewModel.user.value.fsId.value,
                                            viewModel.user.value.authId,
                                            viewModel.user.value.name,
                                            viewModel.user.value.phoneNumber,
                                            viewModel.user.value.email,
                                            password.value,
                                            viewModel.user.value.funds.amount.value
                                            )
                                        Constants.REPOSITORY.addUserRoomItem(userRoomItem)
                                    }
                                    viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                                }
                            }
                        }
                    } else {
                        viewModel.useCases.signInRoomUseCase.execute(context,viewModel, LoginModel(email.value, password.value)){result ->
                            if (result) {
                                viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN

                            }
                        }
                    }
                }
            }) {
            Text(text = stringResource(id = R.string.sign_in))
        }
    }
}