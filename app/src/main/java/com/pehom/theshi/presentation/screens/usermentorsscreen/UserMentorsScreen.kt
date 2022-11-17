package com.pehom.theshi.presentation.screens.usermentorsscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.R
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
                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    elevation = 5.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart
                        ){
                        Text(text = item.name + " ${item.phone}")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}