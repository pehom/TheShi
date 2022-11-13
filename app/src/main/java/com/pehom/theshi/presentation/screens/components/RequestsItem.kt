package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Mentor
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun RequestsItem(
        name: String,
        phone: String,
        viewModel: MainViewModel,
        requestAdd: RequestAdd,
        requestsList: SnapshotStateList<RequestAdd>
    ){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart
            ){
                Text(text = "$name  $phone ")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart
            ){
                Button(onClick = {
                    viewModel.useCases.cancelRequestAddFsUseCase.execute(requestAdd, viewModel){
                        requestsList.remove(requestAdd)
                    }
                }) {
                    Icon(painterResource(id = R.drawable.ic_baseline_cancel_mk2_24), contentDescription = "decline" )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            if (requestAdd.senderPhone != viewModel.user.value.phoneNumber){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Button(onClick = {
                        viewModel.useCases.acceptRequestAddUseCase.execute(requestAdd, viewModel){
                            requestsList.remove(requestAdd)
                            val mentor = Mentor(requestAdd.senderFsId.value, requestAdd.senderPhone, requestAdd.senderName)
                            viewModel.useCases.addMentorFsUseCase.execute(viewModel.user.value, mentor){}
                        }
                    }) {
                        Icon(painterResource(id = R.drawable.ic_baseline_done_24), contentDescription = "accept" )
                    }
                }
            }
        }
    }
}