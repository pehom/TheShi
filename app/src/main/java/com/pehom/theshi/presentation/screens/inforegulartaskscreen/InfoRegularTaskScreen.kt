package com.pehom.theshi.presentation.screens.inforegulartaskscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.screens.components.DialogBuyVocabulary
import com.pehom.theshi.presentation.screens.components.DialogCancelTask
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable

fun InfoRegularTaskScreen(
    viewModel: MainViewModel,
    taskRoomItem: MutableState<TaskRoomItem>
    ){
    Log.d("ppp", "InfoRegularTaskScreen is on")

    val currentTaskRoomItem = taskRoomItem.value
    val isAvailable = remember { mutableStateOf(currentTaskRoomItem.isAvailable) }
    val wordsFs = remember {mutableStateListOf<VocabularyItemScheme>()}
    var vcb : Vocabulary
    if (currentTaskRoomItem.isAvailable)
    {
        val vcbTitle = remember { mutableStateOf(VocabularyTitle("vcbTitle"))}
        val wordsFromRoom = remember { mutableStateListOf<VocabularyItemScheme>() }
        LaunchedEffect(key1 = null) {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.useCases.getVocabularyByFsDocRefRoomUseCase.execute(
                    viewModel,
                    taskRoomItem.value.vcbFsDocRefPath
                ) { vocabulary ->
                    if (vocabulary != null) {
                        vcb = vocabulary
                        vcbTitle.value = vcb.title
                        wordsFromRoom += vcb.items
                        viewModel.useCases.setTaskByVocabulary.execute(viewModel, vocabulary, taskRoomItem) {}
                    } else {
                        viewModel.useCases.readVcbTitleByFsDocRefPathFsUseCase.execute(currentTaskRoomItem.vcbFsDocRefPath) { _vcbTitle ->
                            vcbTitle.value = _vcbTitle
                            viewModel.useCases.readVcbItemsByVcbDocRefFsUseCase.execute(currentTaskRoomItem.vcbFsDocRefPath){_vcbItems ->
                                val vocabularyFs = Vocabulary(vcbTitle.value, _vcbItems)
                                vcb = vocabularyFs
                                viewModel.useCases.setTaskByVocabulary.execute(viewModel, vocabularyFs, taskRoomItem){}
                                viewModel.useCases.saveAvailableVocabularyRoomUseCase.execute(viewModel, vocabularyFs)
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarTaskInfo(
                viewModel= viewModel,
                vcbTitle = vcbTitle,
                isAvailable = isAvailable,
                words = wordsFromRoom
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(7f)
                    .padding(10.dp),
                elevation = 5.dp
            ) {
                Column() {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            // .padding(vertical = 7.dp)
                            .weight(7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        itemsIndexed(wordsFromRoom) {_, item ->
                            Text(text = item.trans, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
    else {
        if (isNetworkAvailable()){
            val vcbTitle = remember { mutableStateOf(VocabularyTitle(currentTaskRoomItem.vcbTitle)) }
            var isVcbLoaded = false
            viewModel.loadedVocabularies.forEachIndexed { index, vocabulary ->
                if (vocabulary.title.value == vcbTitle.value.value)
                    isVcbLoaded = true
            }
            if (!isVcbLoaded){
                viewModel.useCases.readVcbTitleByFsDocRefPathFsUseCase.execute(currentTaskRoomItem.vcbFsDocRefPath) {
                    vcbTitle.value = it
                    Log.d("zzzz", "vcbTitleFs.value = ${vcbTitle.value}")
                    viewModel.useCases.readVcbItemsByVcbDocRefFsUseCase.execute(currentTaskRoomItem.vcbFsDocRefPath){
                        Log.d("zzzz", "readVcbItemsByVcbDocRefFsUseCase it = $it")
                        wordsFs += it
                        viewModel.loadedVocabularies.add(Vocabulary(vcbTitle.value, wordsFs))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TopBarTaskInfo(
                    viewModel= viewModel,
                    vcbTitle = vcbTitle,
                    isAvailable = isAvailable,
                    words = wordsFs
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(7f)
                        .padding(10.dp),
                    elevation = 5.dp
                ) {
                    Column() {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                // .padding(vertical = 7.dp)
                                .weight(7f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            itemsIndexed(wordsFs) {_, item ->
                                Text(text = item.trans, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TopBarTaskInfo(
    viewModel:MainViewModel,
    vcbTitle: MutableState<VocabularyTitle>,
    isAvailable: MutableState<Boolean>,
    words: MutableList<VocabularyItemScheme>
    ){
    val currentTaskRoomItem = viewModel.currentTaskRoomItem.value
    val userFsId = viewModel.user.value.fsId.value
    val dialogBuyVcbState = remember { mutableStateOf(false) }
    val dialogCancelTaskState = remember { mutableStateOf(false) }
    val mentorName = remember{ mutableStateOf("You")}
    if (currentTaskRoomItem.mentorFsId != userFsId) {
        LaunchedEffect(key1 = null ) {
            Constants.REPOSITORY.readMentorRoomItemByMentorFsId(currentTaskRoomItem.mentorFsId){
                if (it != null) {
                    mentorName.value = it.name
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //  .height(56.dp)
            .background(
                Brush.horizontalGradient(
                    (currentTaskRoomItem.progress.toFloat() / 100) to Color.Green,
                    (0.05f + currentTaskRoomItem.progress.toFloat() / 100) to Color.Red,
                    startX = 0.1f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp), contentAlignment = Alignment.CenterStart){
                Text(text = stringResource(id = R.string.mentor) + ": " + (mentorName.value), fontSize = 13.sp )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(3f), contentAlignment = Alignment.Center){
                Text(currentTaskRoomItem.taskTitle, fontSize = 17.sp )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.CenterEnd){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(".. ${currentTaskRoomItem.progress}%", fontSize = 13.sp )
                    Spacer(modifier = Modifier.width(5.dp))
                    DialogCancelTask( viewModel,  dialogCancelTaskState)
                    Icon(painterResource(id = R.drawable.ic_baseline_cancel_mk2_24), "cancel task",
                        modifier = Modifier.clickable {
                            dialogCancelTaskState.value = true
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp), contentAlignment = Alignment.CenterStart){
                Text(text = stringResource(id = R.string.vocabulary) + ":", fontSize = 13.sp )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(3f), contentAlignment = Alignment.Center){
                Text(vcbTitle.value.value, fontSize = 17.sp )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 6.dp)
                .clickable {
                    if (!currentTaskRoomItem.isAvailable)
                        dialogBuyVcbState.value = true
                }, contentAlignment = Alignment.CenterEnd){
                val vcbForDialog = Vocabulary(vcbTitle.value, words.toMutableList())
                DialogBuyVocabulary(viewModel, currentTaskRoomItem.vcbFsDocRefPath, isAvailable, currentTaskRoomItem, dialogBuyVcbState, vcbForDialog )

                Text(
                    if (isAvailable.value) stringResource(id = R.string.available)
                    else stringResource(id = R.string.not_available),
                    fontSize = 13.sp )
            }
        }
    }
}

