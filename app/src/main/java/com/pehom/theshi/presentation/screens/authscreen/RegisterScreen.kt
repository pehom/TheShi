package com.pehom.theshi.presentation.screens.authscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.UserRoomItem
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    Log.d("ppp", "RegisterScreen is on")

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val isPhoneNumberValid = remember { mutableStateOf(false)}
    val isErrorState = remember { mutableStateOf(false)}
    Column(
        Modifier.fillMaxSize(),
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
            value = phoneNumber.value,
            onValueChange = {phoneNumber.value = it},
            label = { Text(text = stringResource(id = R.string.enter_your_phone_number)) },
            placeholder = { Text(text = stringResource(id = R.string.phone_number_placeholder)) },
            //  visualTransformation = PhoneNumberVisualTransformation(),
            singleLine = true,
            isError = isErrorState.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        findPhoneNumber(phoneNumber.value, isPhoneNumberValid) {
                            if (isPhoneNumberValid.value) {
                                isErrorState.value = false
                                kc?.hide()
                                focusManager.clearFocus()
                            } else {
                                isErrorState.value = true
                                Toast.makeText(context, context.getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = name.value,
            onValueChange = {name.value = it},
            label = { Text(text = stringResource(id = R.string.enter_your_name)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
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
            enabled = email.value.isNotEmpty() /*&& isPhoneNumberValid.value*/ && password.value.isNotEmpty(),
            onClick = {
                if (isNetworkAvailable()) {
                    findPhoneNumber(phoneNumber.value, isPhoneNumberValid) {
                        if (isPhoneNumberValid.value) {
                            isErrorState.value = false
                            kc?.hide()
                            focusManager.clearFocus()
                        } else {
                            isErrorState.value = true
                            Toast.makeText(context, context.getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show()
                        }
                    }
                    viewModel.useCases.createFirestoreAccountUseCase
                        .execute(viewModel, auth, LoginModel( email.value, password.value, phoneNumber.value), name.value ){createdUser ->
                            if (createdUser != null) {
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    Constants.REPOSITORY.addUserRoomItem(
                                        UserRoomItem(
                                            createdUser.fsId.value,
                                            createdUser.authId,
                                            createdUser.name,
                                            createdUser.phoneNumber,
                                            createdUser.email,
                                            password.value,
                                            createdUser.funds.amount.value,
                                            createdUser.lastIdSfx
                                        )
                                    )
                                    viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                                }
                            }
                        }
                } else {
                    Toast.makeText(context, context.getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show()
                }
            }) {
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}

fun findPhoneNumber(phoneNumber: String, result: MutableState<Boolean>
                    , onResponse: () -> Unit
) {
    Firebase.firestore.collection("Users").get()
        .addOnSuccessListener { docs ->
            for (doc in docs){
                result.value = doc["phoneNumber"].toString() != phoneNumber
            }
            Log.d("docs", "result = ${result.value}")
            onResponse()
        }
}