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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerMentor(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    val newStudentId = remember { mutableStateOf("") }
    val newStudentName = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        val searchingTextFieldBorderState = remember { mutableStateOf(0) }
        val searchingTextFieldIsError = remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.send_request_to_add_new_student), fontSize = 20.sp)
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = newStudentId.value,
                onValueChange = {newStudentId.value = it},
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    /* focusedBorderColor = when (searchingTextFieldBorderState.value) {
                         0 -> MaterialTheme.colors.onSurface
                         1 -> MaterialTheme.colors.error
                         2 -> Color.Green
                         else -> MaterialTheme.colors.onSurface
                     },*/
                    unfocusedBorderColor =
                    if (searchingTextFieldBorderState.value == 1) Color.Green
                    else {
                        MaterialTheme.colors.onSurface}
                    /*when (searchingTextFieldBorderState.value) {
                    0 -> MaterialTheme.colors.onSurface
                    1 -> MaterialTheme.colors.error
                    2 -> Color.Green
                    else -> MaterialTheme.colors.onSurface
                }*/,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    /*when (searchingTextFieldBorderState.value) {
                        0 -> MaterialTheme.colors.onSurface
                        1 -> MaterialTheme.colors.error
                        2 -> Color.Green
                        else -> MaterialTheme.colors.onSurface
                    }*/
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
                label = { if (searchingTextFieldIsError.value) Text(stringResource(id = R.string.phone_not_found))
                else Text(stringResource(id = R.string.new_student_phone_number))
                },
                //  visualTransformation = PhoneNumberVisualTransformation(),
                placeholder = { Text(text = stringResource(id = R.string.phone_number_placeholder)) },
                isError = searchingTextFieldIsError.value,

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        scope.launch {
                            if (findNewStudent(newStudentId.value) != "") {
                                //  searchingTextFieldBorderState.value = 2
                                searchingTextFieldIsError.value = false
                                searchingTextFieldBorderState.value = 1
                                kc?.hide()
                                focusManager.clearFocus()
                            } else {
                                searchingTextFieldIsError.value = true
                                kc?.hide()
                                focusManager.clearFocus()
                            }
                        }
                    })
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = newStudentName.value,
                onValueChange = {newStudentName.value = it},
                label = { Text(text = stringResource(id = R.string.new_student_name)) },
                placeholder = { Text(stringResource(id = R.string.new_student_name_placeholder)) },
                keyboardOptions = KeyboardOptions( imeAction = ImeAction.Done),
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
                .padding(bottom = 15.dp)
            ,
            verticalAlignment = Alignment.CenterVertically){
            Button(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 20.dp),
                onClick = {
                    scope.launch {
                        newStudentId.value = ""
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
                onClick =  {
                    scope.launch {
                        if (searchingTextFieldIsError.value) {
                            Toast.makeText(context, toastText , Toast.LENGTH_SHORT).show()
                        } else {
                            val newStudent = Student(newStudentId.value, newStudentName.value)
                            // TODO sendRequestToAdd(newStudent.id)
                            Log.d("newStudent", "newStudent.name = ${newStudent.name}")
                            viewModel.students.add(newStudent)
                            scaffoldState.drawerState.close()
                            newStudentId.value = ""
                            newStudentName.value = ""
                            searchingTextFieldBorderState.value = 0
                        }
                    }
                } ) {
                Icon(Icons.Filled.Done, "Send request to add")
            }
        }

    }
}

fun findNewStudent(id: String): String {
    if (id == "+79200123456") return id
    else  return ""

}