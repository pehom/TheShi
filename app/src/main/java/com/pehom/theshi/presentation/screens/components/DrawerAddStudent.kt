package com.pehom.theshi.presentation.screens.mentorscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.RequestAdd

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerAddStudent(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    val newStudentPhoneNumber = remember { mutableStateOf("") }
    val newStudentName = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val searchingTextFieldBorderState = remember { mutableStateOf(0) }
    val searchingTextFieldIsError = remember { mutableStateOf(false) }
    val wrongPhoneNumberLabelText = remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.send_request_to_add_new_student),
                    fontSize = 20.sp
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = newStudentPhoneNumber.value,
                onValueChange = {
                    newStudentPhoneNumber.value = it
                    searchingTextFieldIsError.value = false
                },
                singleLine = true,
                label = {
                    if (searchingTextFieldIsError.value) Text(wrongPhoneNumberLabelText.value)
                    else Text(stringResource(id = R.string.new_student_phone_number))
                },
                //  visualTransformation = PhoneNumberVisualTransformation(),
                placeholder = { Text(text = stringResource(id = R.string.phone_number_placeholder)) },
                isError = searchingTextFieldIsError.value,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        scope.launch {
                            kc?.hide()
                            focusManager.clearFocus()
                        }
                    })
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = newStudentName.value,
                onValueChange = { newStudentName.value = it },
                label = { Text(text = stringResource(id = R.string.new_student_name)) },
                placeholder = { Text(stringResource(id = R.string.new_student_name_placeholder)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        scope.launch {
                            focusManager.clearFocus()
                            kc?.hide()
                        }
                    })
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 20.dp),
                onClick = {
                    scope.launch {
                        newStudentPhoneNumber.value = ""
                        newStudentName.value = ""
                        searchingTextFieldBorderState.value = 0
                        scaffoldState.drawerState.close()
                    }
                }) {
                Icon(Icons.Filled.Close, "Cancel")
            }
            val toastText = context.getString(R.string.phone_not_found)
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                enabled = !searchingTextFieldIsError.value && newStudentName.value.isNotEmpty(),
                onClick = {
                    val newStudent = Student(
                        FsId(newStudentPhoneNumber.value),
                        newStudentName.value,
                        newStudentPhoneNumber.value
                    )
                    // TODO sendRequestToAdd(newStudent.id)
                    viewModel.useCases.getUserFsIdByPhoneFsUseCase.execute(newStudentPhoneNumber.value) { receiverFsId ->
                        Log.d("vvv", "receiverFsId = ${receiverFsId.value}")
                        if (receiverFsId.value == "") {
                            wrongPhoneNumberLabelText.value = context.getString(R.string.phone_not_found)
                            searchingTextFieldIsError.value = true
                            // Toast.makeText(context, toastText , Toast.LENGTH_SHORT).show()
                        } else if (receiverFsId.value == viewModel.user.value.fsId.value) {
                            wrongPhoneNumberLabelText.value = context.getString(R.string.wrong_phone_number)
                            searchingTextFieldIsError.value = true

                        } else {
                            val requestAdd = RequestAdd(viewModel.user.value.fsId, receiverFsId)
                            requestAdd.receiverName = newStudentName.value
                            requestAdd.receiverPhone = newStudentPhoneNumber.value
                            requestAdd.senderPhone = viewModel.user.value.phoneNumber
                            requestAdd.senderName = viewModel.user.value.name
                            viewModel.requestsAdd.add(requestAdd)
                            viewModel.useCases.createRequestAddFsUseCase.execute(
                                viewModel,
                                requestAdd
                            ) {}
                            Log.d("newStudent", "newStudent.name = ${newStudent.name}")
                            //  viewModel.students.add(newStudent)
                            scope.launch {
                                scaffoldState.drawerState.close()
                                newStudentPhoneNumber.value = ""
                                newStudentName.value = ""
                                searchingTextFieldBorderState.value = 0
                            }
                        }
                    }

                }) {
                Icon(Icons.Filled.Done, "Send request to add")
            }
        }

    }
}


