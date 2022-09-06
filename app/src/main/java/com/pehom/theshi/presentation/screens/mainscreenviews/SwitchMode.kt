package com.pehom.theshi.presentation.screens.mainscreenviews

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun SwitchMode(viewModel: MainViewModel){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            text = stringResource(id = (R.string.mentor)),
            fontSize = 15.sp,
            modifier = Modifier.padding(end = 40.dp)
        )
        Switch(
            checked = viewModel.switchState.value,
            onCheckedChange = {
                viewModel.toggleSwitch()
                Log.d("tagg", "vm.switchState = ${viewModel.switchState.value}")
                Log.d("tagg", "vm.screenState = ${viewModel.screenState.value}")

            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFFB9730C),
                checkedTrackColor = Color(0xFFFFC107),
                uncheckedThumbColor = Color(0xFFB9730C),
                uncheckedTrackColor = Color(0xFF84CA33)
            )
        )
        Text(
            text = stringResource(id = R.string.student),
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 40.dp)
        )
    }
}