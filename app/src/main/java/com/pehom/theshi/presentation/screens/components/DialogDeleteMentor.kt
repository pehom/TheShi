package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DialogDeleteMentor(
    mentor: MentorRoomItem,
    viewModel: MainViewModel,
    _dialogState: MutableState<Boolean>
){
    val dialogState = remember{_dialogState }
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                DialogDeleteMentorContent(
                    mentor = mentor,
                    viewModel = viewModel,
                    dialogState = dialogState
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}

@Composable
private fun DialogDeleteMentorContent(
    mentor: MentorRoomItem,
    viewModel: MainViewModel,
    dialogState: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Column {
                Box(contentAlignment = Alignment.Center){
                    Text(text = stringResource(id = R.string.delete_mentor))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(contentAlignment = Alignment.Center){
                    Text(text = mentor.name)
                }
            }
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center){
                Button(onClick = {
                    dialogState.value = false
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center){
                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        mentor.mentorStatus = Constants.DISMISSED
                        Constants.REPOSITORY.updateMentorRoomItem(mentor)
                        dialogState.value = false
                    }
                }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            }
        }
    }
}