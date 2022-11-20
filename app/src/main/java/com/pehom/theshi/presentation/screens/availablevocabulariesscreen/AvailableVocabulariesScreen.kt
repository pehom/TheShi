package com.pehom.theshi.presentation.screens.availablevocabulariesscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.presentation.screens.components.Header
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AvailableVocabulariesScreen(
    viewModel: MainViewModel
) {
    val availableVocabularies = Constants.REPOSITORY.readAvailableVocabularyRoomItemsByUserFsId(viewModel.user.value.fsId.value)
        .observeAsState(listOf()).value
    val vcbItems = remember{ mutableStateListOf("") }
    vcbItems.clear()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center){
            Text(text = stringResource(id = R.string.available_vocabularies))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(13f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 5.dp, bottom = 10.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                     //   .weight(1f)
                        ,
                        contentAlignment = Alignment.Center){
                        Text(text = stringResource(id = R.string.vocabularies))
                    }
                    Spacer(modifier = Modifier.height(5.dp) )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                         //   .weight(1f)
                    ){
                        itemsIndexed(availableVocabularies){_, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clickable {
                                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                                            Constants.REPOSITORY.readAvailableWordsRoomItemsByVcbDocRefPath(
                                                item.vcbDocRefPath,
                                                viewModel.user.value.fsId.value
                                            ) { resultList ->
                                                vcbItems.clear()
                                                resultList.forEach {
                                                    vcbItems.add(it.orig)
                                                }
                                            }
                                        }
                                    },
                                elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    contentAlignment = Alignment.CenterStart){
                                    Text(text =item.vcbTitle )
                                }
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 5.dp, bottom = 10.dp),
                elevation = 5.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                      //  .weight(12f)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        ,
                        contentAlignment = Alignment.Center){
                        Text(text = stringResource(id = R.string.items))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(1f)
                    ){
                        itemsIndexed(vcbItems){index, item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp),
                                contentAlignment = Alignment.CenterStart){
                                Text(text =item )
                            }
                        }
                    }
                }
            }
        }
    }
}