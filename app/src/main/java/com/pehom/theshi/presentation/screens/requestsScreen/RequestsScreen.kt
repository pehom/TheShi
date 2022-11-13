package com.pehom.theshi.presentation.screens.requestsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.R
import com.pehom.theshi.presentation.screens.components.RequestsItem

@Composable
fun RequestsScreen(
    viewModel: MainViewModel
) {
  //  val requests = remember {mutableStateListOf<RequestAdd>()}
    val requests = viewModel.requestsAdd

//    viewModel.useCases.readRequestsAddFsUseCase.execute(viewModel){
//        requests.addAll(it)
//    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(5f)
                .padding(10.dp),
            elevation = 5.dp
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(text = stringResource(id = R.string.mentors))
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(7f)
                ){
                    itemsIndexed(requests){ _, item ->
                        if (item.senderFsId.value != viewModel.user.value.fsId.value){
                            RequestsItem(
                                name = item.senderName,
                                phone = item.senderPhone,
                                viewModel,
                                item,
                                requests
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(7.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(5f)
                .padding(10.dp),
            elevation = 5.dp
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(text = stringResource(id = R.string.students))
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(7f)
                ){
                    itemsIndexed(requests){ _, item ->
                        if (item.receiverFsId.value != viewModel.user.value.fsId.value){
                            RequestsItem(
                                name = item.receiverName,
                                phone = item.receiverPhone,
                                viewModel,
                                item,
                                requests
                            )
                        }
                    }
                }
            }
        }
    }
}