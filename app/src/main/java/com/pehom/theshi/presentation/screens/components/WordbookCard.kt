package com.pehom.theshi.presentation.screens.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel

@Composable
fun WordbookCard(viewModel: MainViewModel) {
    val title = stringResource(id = R.string.wordbook)
    val vocabulary = Vocabulary(VocabularyTitle( title), viewModel.wordbook.toMutableList())
    val context = LocalContext.current
    val toastText = stringResource(id = R.string.no_words_in_wordbook)
    val wordbookSize = remember{ mutableStateOf(0) }
    viewModel.useCases.getWordbookSizeByUserFsId.execute(viewModel){
        wordbookSize.value = it
    }
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        elevation = 5.dp) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .clickable {
                if (wordbookSize.value == 0) {
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.screenState.value = viewModel.MODE_WORDBOOK_SCREEN
                }
            }
            , contentAlignment = Alignment.Center){
            Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.wordbook_contains), fontSize = 20.sp)
                Text(text = "${wordbookSize.value} " + stringResource(id = R.string.words), fontSize = 20.sp)
            }
        }
    }
}