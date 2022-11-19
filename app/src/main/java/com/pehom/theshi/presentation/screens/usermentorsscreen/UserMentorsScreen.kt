package com.pehom.theshi.presentation.screens.usermentorsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun UserMentorsScreen(
    viewModel: MainViewModel
){
    
    val mentorsRoom = Constants.REPOSITORY.readMentorRoomItemsByUserFsId(viewModel.user.value.fsId.value)
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
                MentorDetails(
                    mentor = item,
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
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
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
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
                                    Constants.REPOSITORY.updateMentorRoomItem(mentor)
                                    viewModel.useCases.updateMentorNameFsUseCase.execute(
                                        viewModel.user.value,
                                        mentor
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
                    trailingIcon = { Icon(painterResource(id = R.drawable.ic_baseline_edit_24), contentDescription ="edit name icon") },
                    label = { Text(text = stringResource(id = R.string.name))}
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), contentAlignment = Alignment.CenterStart){
                Text(text = stringResource(id = R.string.phone) + ": ${mentor.phone}")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}