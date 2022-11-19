package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.UserRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerUserProfile(viewModel: MainViewModel, scaffoldState: ScaffoldState, auth: FirebaseAuth) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
           // .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserDetails(
            userFsID = viewModel.user.value.fsId.value,
            modifier = Modifier.fillMaxWidth(),
            viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.phone) + ": ${viewModel.user.value.phoneNumber}")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.funds) + ": ${viewModel.user.value.funds.amount.value}")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(
                text = stringResource(id = R.string.available_vocabularies),
                modifier = Modifier.clickable {  })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.mentors),
                modifier = Modifier.clickable {
                    viewModel.screenState.value = viewModel.MODE_USER_MENTORS_SCREEN
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.students),
                modifier = Modifier.clickable {  }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.wordbook),
                modifier = Modifier.clickable {  }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.settings),
                modifier = Modifier.clickable {  }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = stringResource(id = R.string.sign_out),
                modifier = Modifier.clickable {
                    viewModel.useCases.signOutUseCase.execute(viewModel, auth)
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UserDetails(
    userFsID: String,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
    ) {
    val username = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val user = remember { mutableStateOf(UserRoomItem("", "", "", "", "", "", 0)) }
    LaunchedEffect(key1 = null,) {
        val userRoomItem = Constants.REPOSITORY.readUserRoomItemByUserFsId(userFsID)
        if (userRoomItem != null) {
            user.value = userRoomItem
            username.value = userRoomItem.userName
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { username.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch(Dispatchers.IO) {
                        if (username.value != user.value.userName) {
                            user.value.userName = username.value
                            Constants.REPOSITORY.updateUserRoomItem(user.value)
                            viewModel.useCases.updateUsernameFsUseCase.execute(
                                viewModel.user.value.fsId.value,
                                username.value
                            ) {}
                        }
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                }
            ),
            leadingIcon = {
                Image(
                    painterResource(id = R.drawable.ic_profile_image_mock),
                    contentDescription = "profile image",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.Crop
                )
            },
            trailingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_edit_24),
                    contentDescription = "edit name icon"
                )
            },
            label = { Text(text = stringResource(id = R.string.username)) }
        )
    }
}
