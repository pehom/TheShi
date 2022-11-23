package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.VocabularyUploadToFs
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun DialogConfirmVocabularyUpload(
    viewModel: MainViewModel,
    vcbUploadToFs: MutableState<VocabularyUploadToFs>,
    _dialogState: MutableState<Boolean>,
    _progressState: MutableState<Boolean>
){

val dialogState = remember{_dialogState }
if (dialogState.value) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        content = {
            DialogConfirmVocabularyUploadContent(
                viewModel = viewModel,
                dialogState = dialogState,
                vcbUploadToFs = vcbUploadToFs,
                progressState = _progressState
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
private fun DialogConfirmVocabularyUploadContent(
    viewModel: MainViewModel,
    dialogState: MutableState<Boolean>,
    vcbUploadToFs: MutableState<VocabularyUploadToFs>,
    progressState: MutableState<Boolean>
){
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(20.dp)
          //  .background(Color.White)
        ,
        elevation= 5.dp
        ){
        Box(contentAlignment = Alignment.Center){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center){
                    Text(text = stringResource(id = R.string.overwrite_vocabulary_alert))
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row() {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center){
                        Button(onClick = {
                            dialogState.value = false
                        }) {
                            Icon(painterResource(id = R.drawable.ic_baseline_cancel_mk2_24), contentDescription = "back")
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center){
                        Button(onClick = {
                            dialogState.value = false
                            progressState.value = true
                            viewModel.useCases.uploadVocabularyToFsUseCase.execute(vcbUploadToFs.value){
                                progressState.value = false
                            }
                        }) {
                            Icon(painterResource(id = R.drawable.ic_baseline_done_24), contentDescription = "done" )
                        }
                    }
                }
            }
        }
    }
}