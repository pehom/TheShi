package com.pehom.theshi.presentation.screens.mainscreenviews

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "EVAT",
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp
        )
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