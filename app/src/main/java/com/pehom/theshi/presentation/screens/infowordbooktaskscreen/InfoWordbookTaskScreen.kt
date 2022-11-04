package com.pehom.theshi.presentation.screens.infowordbooktaskscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun InfoWordbookTaskScreen(
    viewModel: MainViewModel
){
    Log.d("ppp", "InfoWordbookTaskScreen is on")

    val taskRoomItem = viewModel.currentWordbookTaskRoomItem.value
    val items = viewModel.currentWordbookVocabulary.value.items
    /*LaunchedEffect(key1 = null ) {
        viewModel.useCases.getVocabularyByFsDocRefRoomUseCase.execute(
            viewModel,
            taskRoomItem.vcbFsDocRefPath
        ){
            if (it != null) {
                items.clear()
                items += it.items
            } else {
                viewModel.useCases.readVcbTitleByFsDocRefPathFsUseCase.execute(taskRoomItem.vcbFsDocRefPath){_vcbTitle ->
                    val vcbTitle = _vcbTitle
                    viewModel.useCases.readVcbItemsByVcbDocRefFsUseCase.execute(taskRoomItem.vcbFsDocRefPath){vcbItems ->
                        items.clear()
                        items += vcbItems
                    }
                }
            }
        }
    }*/
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 10.dp),
            elevation = 5.dp
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = taskRoomItem.taskTitle, fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(15f)
                .padding(horizontal = 10.dp),
            elevation = 5.dp
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(items){_, item ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = item.trans, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}