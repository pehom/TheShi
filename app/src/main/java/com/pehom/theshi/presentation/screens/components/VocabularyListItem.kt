package com.pehom.theshi.presentation.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.ui.theme.Purple700
import com.pehom.theshi.ui.theme.Teal200
import com.pehom.theshi.R

@Composable
fun VocabularyListItem(index: Int, title: String, selectedIndex: MutableState<Int>){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .border(
            width = 1.dp,
            color = if (index != selectedIndex.value) Teal200
            else Purple700,
            shape = RoundedCornerShape(10)
        )
        .clickable {
            selectedIndex.value = index
        }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
            Text(title  , fontSize = 17.sp, modifier = Modifier.padding(10.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                    contentDescription = "show words")
            }
    }
}