package com.pehom.theshi.presentation.screens.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun DrawerPendingRequests(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
    ) {
        PendingRequestsView(viewModel = viewModel, scaffoldState = scaffoldState)
    /*Column(modifier = Modifier.fillMaxSize(),
            
        ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(10.dp),
            elevation = 5.dp
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(text = stringResource(id = R.string.sent_requests))
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                    itemsIndexed(viewModel.requestsAdd) { _, item ->
                        RequestAddListItem( item, viewModel)
                    }
                }
            }
        }
    }*/
}

/*@Composable
fun RequestAddListItem(item: RequestAdd, viewModel: MainViewModel ) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "${item.receiverName}  ${item.receiverPhone}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { Icon(Icons.Filled.Delete,
                "cancel the request",
                modifier = Modifier.clickable {
                    viewModel.useCases.cancelRequestAddFsUseCase.execute(item, viewModel) {}
                })}
            )
    }
}*/
