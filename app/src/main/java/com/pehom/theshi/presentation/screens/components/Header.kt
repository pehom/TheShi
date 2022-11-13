package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun Header(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    val drawerType = viewModel.drawerType
    val fundsAmount = remember {viewModel.user.value.funds.amount.value}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
           // horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.CenterStart){
                Icon(
                    Icons.Filled.AccountBox,
                    contentDescription = "User profile",
                    modifier = Modifier

                        .clickable {
                            drawerType.value = Constants.DRAWER_USER_PROFILE
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "EVAT",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Funds: $fundsAmount",
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            IconButton(
                enabled = viewModel.requestsAdd.size > 0,
                onClick = {

                    viewModel.screenState.value = viewModel.MODE_REQUESTS_SCREEN
            }) {
                Text(
                    text = "Pending requests " + viewModel.requestsAdd.size,
                    fontSize = 14.sp,
                    color = if (viewModel.requestsAdd.size > 0) Color.Green
                    else Color.Transparent,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}