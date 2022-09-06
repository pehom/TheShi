package com.pehom.theshi.presentation.screens.testscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Task

@Composable
fun CardStart(
    currentTask: MutableState<Task>,
    isStarted: MutableState<Boolean>
) {

    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
        elevation = 5.dp) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = {
                isStarted.value = true
                currentTask.value.isTestGoing.value = true
            }) {
                Text(text = stringResource(id = R.string.start))
            }
        }
    }
}