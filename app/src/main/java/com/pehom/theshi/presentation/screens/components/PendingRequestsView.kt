package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel


@Composable
fun PendingRequestsView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
){
  //  val pendingRequests = viewModel.requestsAdd
    val scope = rememberCoroutineScope()
    val pendingRequests = remember { viewModel.requestsAdd}
    Log.d("pendingRequestsView", "pendingRequests.size = ${pendingRequests.size}")
    val myFsId = viewModel.user.value.fsId
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp),
        elevation = 5.dp) {
        Column(modifier = Modifier.fillMaxSize()
        ){
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text( text =  stringResource(id =  R.string.pending_Requests) + ":", fontSize = 20.sp,modifier= Modifier.padding(10.dp))
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(7f)
                .padding(10.dp)
            ) {
                itemsIndexed(pendingRequests) {index, item ->
                    if (item.senderFsId.value == myFsId.value){
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)) {
                            Box(contentAlignment = Alignment.CenterStart) {
                                Text(text = "${index+1})  ${item.receiverName}   phone: ${item.receiverPhone}", fontSize = 20.sp,)
                            }
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Box(contentAlignment = Alignment.CenterStart){
                                    Button(onClick = {
                                        viewModel.useCases.cancelRequestAddFsUseCase.execute(item, viewModel){}
                                    }) {
                                        Text("Cancel")
                                    }
                                }
                            }
                        }
                    } else if ( myFsId.value == item.receiverFsId.value) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)) {
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (item.senderFsId.value == myFsId.value)
                                    Text(text = "${index+1})  ${item.receiverName}   phone: ${item.receiverPhone}", fontSize = 20.sp,)
                                else if (item.receiverFsId.value == myFsId.value)
                                    Text(text = "${index+1})  ${item.senderName}  sender phone: ${item.senderPhone}", fontSize = 20.sp,)
                            }
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = {
                                    viewModel.useCases.acceptRequestAddUseCase.execute(item, viewModel) {
                                        viewModel.useCases.readStudentsFsUseCase.execute(viewModel){
                                        }
                                    }
                                }) {
                                    Text("Accept")
                                }
                                Button(onClick = {
                                    viewModel.useCases.declineRequestAddUseCase.execute(item,viewModel){}
                                }) {
                                    Text("Decline")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}