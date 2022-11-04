package com.pehom.theshi.presentation.screens.wordbookscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.WordbookRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WordbookScreen(
    viewModel: MainViewModel
){
    Log.d("ppp", "WordbookScreen is on")

    val vcbList = remember { mutableStateListOf(WordbookRoomItem()) }
    LaunchedEffect(key1 = null ) {
        vcbList.clear()
        vcbList.addAll(Constants.REPOSITORY.readWordbookRoomItemsByUserFsId(viewModel.user.value.fsId.value))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
            elevation = 5.dp
        ) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = stringResource(id = R.string.wordbook))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
            elevation = 5.dp
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.useCases.setAllWordsWordbookTaskUseCase.execute(viewModel){
                        viewModel.screenState.value = viewModel.MODE_WORDBOOK_TASK_SCREEN
                    }
                },
                contentAlignment = Alignment.Center){
                Text(text = stringResource(id = R.string.all_words))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Card(
            modifier = Modifier.fillMaxSize().fillMaxHeight().weight(12f),
            elevation = 5.dp
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
                    Text(text = stringResource(id = R.string.vocabularies) + ":")
                }
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp)
                ){
                    itemsIndexed(vcbList){_, item ->
                        VcbListItem(viewModel, item)
                    }
                }
            }
        }
    }
}

@Composable
private fun VcbListItem(
    viewModel: MainViewModel,
    wordbookRoomItem: WordbookRoomItem
){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable {
                viewModel.useCases.getVocabularyByFsDocRefRoomUseCase.execute(
                    viewModel,
                    wordbookRoomItem.vcbDocRefPath
                ){vocabulary ->
                    if (vocabulary != null) {
                        viewModel.currentWordbookTaskRoomItem.value = TaskRoomItem(
                            id = Constants.WORDBOOK_TASK_ROOM_ITEM,
                            mentorFsId = viewModel.user.value.fsId.value,
                            studentFsId = viewModel.user.value.fsId.value,
                            taskTitle = wordbookRoomItem.vcbTitle,
                            vcbFsDocRefPath = wordbookRoomItem.vcbDocRefPath,
                            isAvailable = true
                        )
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            Constants.REPOSITORY.createTaskRoomItem(viewModel.currentWordbookTaskRoomItem.value){}
                        }
                        viewModel.useCases.addUserTaskFsUseCase.execute(viewModel, viewModel.currentWordbookTaskRoomItem.value){}
                        viewModel.useCases.setTaskByVocabulary.execute(viewModel, vocabulary, viewModel.currentWordbookTaskRoomItem){
                            viewModel.currentWordbookVocabulary.value = vocabulary
                            viewModel.screenState.value = viewModel.MODE_WORDBOOK_TASK_SCREEN
                        }
                    } else {
                        Toast.makeText(context, "vocabulary not found", Toast.LENGTH_SHORT).show()
                    }
                }
            },
        contentAlignment = Alignment.Center){
        Text(text = wordbookRoomItem.vcbTitle)
    }
}