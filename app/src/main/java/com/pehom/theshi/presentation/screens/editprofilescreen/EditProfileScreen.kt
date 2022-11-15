package com.pehom.theshi.presentation.screens.editprofilescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileScreen(
    viewModel: MainViewModel
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val editUserName = remember { mutableStateOf(viewModel.user.value.name)  }
    val labelText = remember { mutableStateOf("username: ${viewModel.user.value.name}") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
         OutlinedTextField(
             value = editUserName.value ,
             onValueChange = {editUserName.value = it},
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 20.dp),
             label = { Text(text = labelText.value)},
             trailingIcon = {
                 IconButton(
                     enabled = editUserName.value.isNotEmpty(),
                     onClick = {
                         viewModel.user.value.name = editUserName.value
                         labelText.value = "username: ${viewModel.user.value.name}"
                         keyboardController?.hide()
                         focusManager.clearFocus()
                     }) {
                     Icon(painterResource(id = R.drawable.ic_baseline_done_24), contentDescription = "apply changes" )
                 }
             }
             )
    }
}