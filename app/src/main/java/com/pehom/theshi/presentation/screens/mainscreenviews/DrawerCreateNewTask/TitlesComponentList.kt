package com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.screens.mainscreenviews.VocabularyListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TitlesComponentList(
    titles: List<VocabularyTitle>,
    vocabulariesListState: LazyListState,
    selectedIndex: MutableState<Int>){
    LazyColumn(
        state = vocabulariesListState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        /* scope.launch {
             vocabularyTitles.clear()
             vocabularyTitles += viewModel.allVocabularyTitles
         }*/
        itemsIndexed(titles) { index, item ->
            VocabularyListItem(index, item.title, selectedIndex)
        }
    }
}