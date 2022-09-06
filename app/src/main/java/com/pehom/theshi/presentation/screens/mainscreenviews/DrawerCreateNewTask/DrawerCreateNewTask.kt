package com.pehom.theshi.presentation.screens.mainscreenviews

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask.TitlesComponentList
import com.pehom.theshi.presentation.screens.mainscreenviews.DrawerCreateNewTask.TitlesLoading

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerCreateNewTask(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val vocabularies = viewModel.vocabularies
    val newTaskTitle = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchingValue = remember { mutableStateOf("") }
    val vocabulariesListState = rememberLazyListState()
    val selectedIndex = remember { mutableStateOf(-1) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val vocabularyTitles = remember {viewModel.allVocabularyTitles}
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = newTaskTitle.value,
            onValueChange = {newTaskTitle.value = it},
            label = { Text(text = stringResource(id = R.string.new_task_title)) },
            placeholder = { Text(stringResource(id = R.string.new_task_title)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                })
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = searchingValue.value,
            onValueChange = { newText ->
                run{searchingValue.value = newText
                    vocabularies.forEachIndexed{index, vocabulary ->
                        if (vocabulary.title.contains(searchingValue.value, true)) {
                            scope.launch {
                                vocabulariesListState.animateScrollToItem(index)
                            }
                        }
                    }}},
            textStyle = TextStyle(fontSize = 17.sp),
            placeholder = { Text(stringResource(id = R.string.find_vocabulary), fontSize = 17.sp) },
            trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "", Modifier.size(30.dp)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    vocabularies.forEachIndexed{index, vocabulary ->
                        if (searchingValue.value.lowercase() == vocabulary.title.lowercase()) {
                            scope.launch {
                                vocabulariesListState.animateScrollToItem(index)
                                keyboardController?.hide()
                            }
                        } else {
                            Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show()
                            keyboardController?.hide()
                        }
                        focusManager.clearFocus()
                    }
                }
            )
        )
        if (vocabularyTitles.isNullOrEmpty())
            TitlesLoading()
        else
            TitlesComponentList(vocabularyTitles, vocabulariesListState, selectedIndex)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalAlignment = Alignment.CenterVertically){
            Button(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 20.dp),
                onClick = {
                    scope.launch {
                        selectedIndex.value = -1
                        searchingValue.value = ""
                        newTaskTitle.value = ""
                        scaffoldState.drawerState.close()
                    }
                }) {
                Icon(Icons.Filled.Close, "Cancel")
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                enabled = selectedIndex.value!=-1,
                onClick =  {
                    val selectedVocabulary = getVocabularyByTitle(vocabularyTitles[selectedIndex.value].title)
                    if (!viewModel.isStudentProfileShown.value){
                        if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].title
                        viewModel.tasks.add( Task(newTaskTitle.value, selectedVocabulary))
                    }
                    else {
                        if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].title
                        viewModel.students[viewModel.studentNumber.value].
                        tasks.add( Task(newTaskTitle.value, selectedVocabulary))
                    }

                    scope.launch {
                        scaffoldState.drawerState.close()
                        selectedIndex.value = -1
                        searchingValue.value = ""
                        newTaskTitle.value = ""
                    }
                } ) {
                Icon(Icons.Filled.Done, "Add new task")
            }
        }
    }
}
