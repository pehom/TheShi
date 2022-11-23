package com.pehom.theshi.presentation.screens.usermentorsscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.presentation.screens.components.DialogDeleteMentor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun UserMentorsScreen(
    viewModel: MainViewModel
){
    
    val mentorsRoom = Constants.REPOSITORY.readMentorRoomItemsByUserFsIdAsLiveData(viewModel.user.value.fsId.value)
        .observeAsState(listOf()).value
    LaunchedEffect(key1 = null ){
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            Constants.REPOSITORY.getMentorRoomItemsCountByUserFsId(viewModel.user.value.fsId.value){
                if (it == 0) {
                    viewModel.useCases.readAllUserMentorsFsUseCase.execute(viewModel){
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            it.forEach {mentorsRoomItem ->
                                Constants.REPOSITORY.addMentorRoomItem(mentorsRoomItem)
                            }
                        }
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ){
            Text(text = stringResource(id = R.string.mentors))
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(17f)
        ){
            itemsIndexed(mentorsRoom){index, item ->
                if (item.mentorStatus != Constants.DISMISSED){
                    MentorDetails(
                        mentor = item,
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MentorDetails(
    mentor: MentorRoomItem,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
){
    val mentorName = remember { mutableStateOf(mentor.name) }
    val mentorStatus = remember { mutableStateOf(mentor.mentorStatus) }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val dialogState = remember { mutableStateOf(false) }
    DialogDeleteMentor(
        mentor = mentor ,
        viewModel = viewModel,
        _dialogState = dialogState )
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.CenterStart){
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = mentorName.value,
                    onValueChange = {mentorName.value = it},
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            scope.launch(Dispatchers.IO) {
                                if (mentorName.value != mentor.name) {
                                    mentor.name = mentorName.value
                                    mentor.hasChanges = true
                                    Constants.REPOSITORY.updateMentorRoomItem(mentor)
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
                    trailingIcon = { Icon(painterResource(id = R.drawable.ic_baseline_edit_24), contentDescription ="edit name icon") },
                    label = { Text(text = stringResource(id = R.string.name))}
                )
            }
           // Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f),
                    contentAlignment = Alignment.CenterStart){
                    Text(text = stringResource(id = R.string.phone) + ": ${mentor.phone}")
                }
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(onClick = {
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        when(mentorStatus.value){
                            Constants.ACCEPTED -> {
                                mentorStatus.value = Constants.BLOCKED
                                mentor.mentorStatus = Constants.BLOCKED
                                mentor.hasChanges = true
                                Constants.REPOSITORY.updateMentorRoomItem(mentor)
                            }
                            Constants.BLOCKED -> {
                                mentorStatus.value = Constants.ACCEPTED
                                mentor.mentorStatus = Constants.ACCEPTED
                                mentor.hasChanges = true
                                Constants.REPOSITORY.updateMentorRoomItem(mentor)
                            }
                        }
                    }
                }) {
                    when (mentorStatus.value){
                        Constants.ACCEPTED -> {
                            Log.d(Constants.INSPECTING_TAG, "mentorStatus = ${mentorStatus.value} ")
                            Icon(
                                painterResource(id = R.drawable.ic_baseline_not_blocked_24),
                                contentDescription ="Block",
                            )
                        }
                        Constants.BLOCKED -> {
                            Log.d(Constants.INSPECTING_TAG, "mentorStatus = ${mentorStatus.value} ")
                            Icon(
                                painterResource(id = R.drawable.ic_baseline_blocked_24),
                                contentDescription ="Unblock",
                                tint = Color.Unspecified)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(onClick = {
                    dialogState.value = true
                }) {
                    Icon(painterResource(id = R.drawable.ic_baseline_cancel_mk2_24), contentDescription ="Delete" )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }

    }
}