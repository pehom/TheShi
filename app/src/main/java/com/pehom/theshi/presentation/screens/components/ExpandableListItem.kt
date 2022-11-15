package com.pehom.theshi.presentation.screens.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.ui.theme.Purple700
import com.pehom.theshi.ui.theme.Teal200

@Composable
fun ExpandableListItem(
    index: Int,
    vcbTitle: VocabularyTitle,
    selectedIndex: MutableState<Int>,
    onClickItem: () -> Unit,
    expanded: Boolean,
    viewModel: MainViewModel
){
    Column() {
        Header(index = index, title = vcbTitle.value, selectedIndex = selectedIndex, onClickItem = onClickItem)
        ExpandableList(vcbTitle = vcbTitle, isExpanded = expanded, viewModel = viewModel)
    }
}

@Composable
private fun Header(index: Int, title: String, selectedIndex: MutableState<Int>, onClickItem: () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .border(
            width = 1.dp,
            color = if (index != selectedIndex.value) Teal200
            else Purple700,
            shape = RoundedCornerShape(10)
        )
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            selectedIndex.value = index
        }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(title  , fontSize = 17.sp, modifier = Modifier.padding(10.dp))
        IconButton(onClick = {
            onClickItem()
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = "show words")
        }
    }
}

@Composable
private fun ExpandableList(vcbTitle: VocabularyTitle, isExpanded: Boolean, viewModel: MainViewModel) {
    val items = remember { mutableStateListOf("") }
    items.clear()
    viewModel.useCases.readVcbItemsByVcbDocRefFsUseCase.execute(vcbTitle.fsDocRefPath){
        it.forEach { vcbItemScheme ->
            items.add(vcbItemScheme.orig)
        }
    }

    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 10.dp)
        ) {
            itemsIndexed(items){_, item ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center){
                        Text(text = item)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

