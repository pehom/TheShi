package com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TitlesLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally))
    }
}