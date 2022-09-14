package com.pehom.theshi.presentation.screens.mainscreenviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun Header(drawerType: MutableState<Int>, scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "EVAT",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Funds",
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "Score",
                fontSize = 14.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}