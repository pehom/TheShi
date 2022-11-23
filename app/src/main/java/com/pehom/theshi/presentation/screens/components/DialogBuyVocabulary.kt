package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DialogBuyVocabulary(
    viewModel: MainViewModel,
    vcbDocRefPath: String,
    isAvailable: MutableState<Boolean>,
    taskRoomItem: TaskRoomItem,
    _dialogState: MutableState<Boolean>,
    vcbForDialog: Vocabulary
){
    val dialogState = remember{_dialogState }
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                DialogContent(
                    viewModel = viewModel,
                    vcbDocRefPath = vcbDocRefPath,
                    isAvailable = isAvailable,
                    taskRoomItem = taskRoomItem,
                    dialogState = dialogState,
                    vcbForDialog = vcbForDialog)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}

@Composable
fun DialogContent(
    viewModel: MainViewModel,
    vcbDocRefPath: String,
    isAvailable: MutableState<Boolean>,
    taskRoomItem: TaskRoomItem,
    dialogState: MutableState<Boolean>,
    vcbForDialog: Vocabulary
) {
    val context = LocalContext.current
    val price = remember { mutableStateOf(0) }
    val vocabularyTitle = remember { mutableStateOf("") }
    Firebase.firestore.document(vcbDocRefPath).get()
        .addOnSuccessListener {
            price.value = it[Constants.PRICE].toString().toInt()
            vocabularyTitle.value = it[Constants.VOCABULARY_TITLE].toString()
        }
        .addOnFailureListener {
            Log.d("dialogContent", "getting vocabularyByFsDocRef failed, Error: ${it.message}")
        }
        Column(
            modifier= Modifier
                // .padding(vertical = 100.dp, horizontal = 30.dp)
                //.background(Color.White)
                // .wrapContentSize(Alignment.Center),
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Text(text = stringResource(id = R.string.dialog_buy_text), fontSize = 16.sp )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Text(text = vocabularyTitle.value, fontSize = 16.sp)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(5f),
                contentAlignment = Alignment.Center
            ){
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    elevation = 4.dp
                ) {
                    LazyColumn() {
                        itemsIndexed(vcbForDialog.items){index, item ->
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                                Text(text = item.orig )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                            .padding(start = 5.dp),
                        onClick = {
                        dialogState.value = false
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                            .padding(end = 5.dp),
                        enabled = price.value <= viewModel.user.value.funds.amount.value,
                        onClick = {
                            //TODO buyVocabularyUseCase
                            viewModel.useCases.buyVocabularyWithLoadedItemsUseCase.execute(viewModel, price.value, context, vcbForDialog ){
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    Constants.REPOSITORY.updateTaskRoomItem(taskRoomItem){
                                        viewModel.viewModelScope.launch(Dispatchers.Main) {
                                            isAvailable.value = true
                                            viewModel.currentTaskRoomItem.value.isAvailable = true
                                            viewModel.useCases.setTaskByVocabulary.execute(viewModel, vcbForDialog, viewModel.currentTaskRoomItem){
                                                dialogState.value = false
                                            }
                                        }
                                    }
                                }
                            }
                        }) {
                        Text(text = stringResource(id = R.string.pay) + " ${price.value} "
                                + stringResource(id = R.string.funds)
                        )
                    }
                }
            }
        }
}