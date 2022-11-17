package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.ui.theme.Purple700
import com.pehom.theshi.ui.theme.Teal200
import kotlinx.coroutines.launch

@Composable
fun ExpandableListItem(
    index: Int,
    vcbTitle: VocabularyTitle,
    selectedIndex: MutableState<Int>,
    onClickItem: () -> Unit,
    expanded: Boolean,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = LazyListState()
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Header(index = index, title = vcbTitle.value, selectedIndex = selectedIndex, onClickItem = onClickItem, lazyListState)
        ExpandableList(vcbTitle = vcbTitle, isExpanded = expanded, viewModel = viewModel)
    }
}

@Composable
private fun Header(
    index: Int, title: String,
    selectedIndex: MutableState<Int>,
    onClickItem: () -> Unit,
    lazyListState: LazyListState
    ){
    val scope = rememberCoroutineScope()
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
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
            if (selectedIndex.value != index) {
                selectedIndex.value = index
            } else {
                selectedIndex.value = -1
            }
        }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(title  , fontSize = 17.sp, modifier = Modifier.padding(10.dp))
        IconButton(onClick = {
            scope.launch {
                if (index > 4) {
                    lazyListState.animateScrollToItem(index -4)
                }
              //  lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                onClickItem()
            }
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = "show words")
        }
    }
}

@Composable
private fun ExpandableList(
    vcbTitle: VocabularyTitle,
    isExpanded: Boolean,
    viewModel: MainViewModel
    ) {
    // val items = viewModel.vocabularyTitlesListItemOrigItems[vcbTitle.fsDocRefPath]?.observeAsState(listOf())?.value
    val items = remember { viewModel.vocabularyTitlesListItemOrigItems[vcbTitle.fsDocRefPath] }
    if (items != null) {
        Log.d("exp", "items.size = ${items.size}")
    } else {
        Log.d("exp", "items = null")

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
                .padding(horizontal = 20.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
        ) {
            if (items != null){
                itemsIndexed(items) { _, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item)
                    }
                    //  Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

