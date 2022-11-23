package com.pehom.theshi.presentation.screens.components.drawercreatenewtask

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.TaskInfo
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.screens.components.ExpandableListItem
import com.pehom.theshi.presentation.screens.components.VocabularyListItem
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.TaskIdFactory
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerCreateNewTask(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val TAG = "DrawerCreateNewTask"
    Log.d(TAG, "drawer create new task has been invoked")
    val newTaskTitle = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchingValue = remember { mutableStateOf("") }
    val vocabulariesListState = rememberLazyListState()
    val selectedIndex = remember { mutableStateOf(-1) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val vocabularyTitles = remember { viewModel.allVocabularyTitles }
  //  Log.d(Constants.INSPECTING_TAG, "$TAG vocabularyTitles[0] = ${vocabularyTitles[0]}")

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
                run { searchingValue.value = newText
                      vocabularyTitles.forEachIndexed{index, vocabularyTitle ->
                        if (vocabularyTitle.value.contains(searchingValue.value, true)) {
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
                    vocabularyTitles.forEachIndexed{index, vocabularyTitle ->
                        if (searchingValue.value.lowercase() == vocabularyTitle.value.lowercase()) {
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
        if (vocabularyTitles.isNullOrEmpty()) {
            TitlesLoading()
            viewModel.useCases.getAllVocabularyTitlesFsUseCase.execute(viewModel){
                viewModel.allVocabularyTitles.clear()
                viewModel.allVocabularyTitles.addAll(it)
            }
        }
        else
            TitlesComponentList(vocabularyTitles, vocabulariesListState, selectedIndex, viewModel)
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
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.readAvailableVocabularyRoomItemByVcbDocRefPath(
                            vocabularyTitles[selectedIndex.value].fsDocRefPath,
                            viewModel.user.value.fsId.value){shet ->
                            if (shet != null) {
                                if (!viewModel.isStudentProfileShown.value){
                                    if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].value
                                    val newTask = TaskRoomItem(
                                        TaskIdFactory.createId(viewModel),
                                        //  viewModel.taskIdFactory.createId(),
                                        viewModel.user.value.fsId.value,
                                        viewModel.user.value.fsId.value,
                                        newTaskTitle.value,
                                        vocabularyTitles[selectedIndex.value].fsDocRefPath,
                                        vocabularyTitles[selectedIndex.value].value,
                                        true,
                                        0,
                                        0,
                                        0,
                                        0,
                                        mutableMapOf(),
                                        Constants.STATUS_IN_PROGRESS
                                    )
                                    viewModel.useCases.addUserTaskFsUseCase.execute(viewModel, newTask){}
                                    viewModel.useCases.updateLastTaskIdSfxFsUseCase.execute(viewModel.user.value.lastIdSfx, viewModel.user.value.fsId.value) {}
                                    viewModel.useCases.addTaskRoomUseCase.execute(viewModel, newTask) {
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                            selectedIndex.value = -1
                                            searchingValue.value = ""
                                            newTaskTitle.value = ""
                                        }
                                    }
                                }
                                else {
                                    if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].value
                                    val newTask = TaskRoomItem(
                                        TaskIdFactory.createId(viewModel),
                                        // viewModel.taskIdFactory.createId(),
                                        viewModel.user.value.fsId.value,
                                        viewModel.currentStudent.value.fsId.value,
                                        newTaskTitle.value,
                                        vocabularyTitles[selectedIndex.value].fsDocRefPath,
                                        vocabularyTitles[selectedIndex.value].value,
                                        true,
                                        0,
                                        0,
                                        0,
                                        0,
                                        mutableMapOf(),
                                        Constants.STATUS_IN_PROGRESS
                                    )
                                    viewModel.useCases.addStudentTaskFsUseCase.execute(viewModel, newTask){}
                                    viewModel.useCases.updateLastTaskIdSfxFsUseCase.execute(viewModel.user.value.lastIdSfx, viewModel.user.value.fsId.value) {}
                                    viewModel.studentTasks.add(TaskInfo(newTask.id, newTask.taskTitle,  vocabularyTitles[selectedIndex.value], Constants.STATUS_IN_PROGRESS))
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                        selectedIndex.value = -1
                                        searchingValue.value = ""
                                        newTaskTitle.value = ""
                                    }
                                }
                            } else {
                                if (!viewModel.isStudentProfileShown.value){
                                    if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].value
                                    val newTask = TaskRoomItem(
                                        TaskIdFactory.createId(viewModel),
                                      //  viewModel.taskIdFactory.createId(),
                                        viewModel.user.value.fsId.value,
                                        viewModel.user.value.fsId.value,
                                        newTaskTitle.value,
                                        vocabularyTitles[selectedIndex.value].fsDocRefPath,
                                        vocabularyTitles[selectedIndex.value].value,
                                        false,
                                        0,
                                        0,
                                        0,
                                        0,
                                        mutableMapOf(),
                                        Constants.STATUS_IN_PROGRESS

                                    )
                                    viewModel.useCases.addUserTaskFsUseCase.execute(viewModel, newTask){}
                                    viewModel.useCases.updateLastTaskIdSfxFsUseCase.execute(viewModel.user.value.lastIdSfx, viewModel.user.value.fsId.value) {}
                                    viewModel.useCases.addTaskRoomUseCase.execute(viewModel, newTask) {
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                            selectedIndex.value = -1
                                            searchingValue.value = ""
                                            newTaskTitle.value = ""
                                        }
                                    }
                                }
                                else {
                                    if (newTaskTitle.value == "") newTaskTitle.value = vocabularyTitles[selectedIndex.value].value
                                    val newTask = TaskRoomItem(
                                        TaskIdFactory.createId(viewModel),
                                       // viewModel.taskIdFactory.createId(),
                                        viewModel.user.value.fsId.value,
                                        viewModel.currentStudent.value.fsId.value,
                                        newTaskTitle.value,
                                        vocabularyTitles[selectedIndex.value].fsDocRefPath,
                                        vocabularyTitles[selectedIndex.value].value,
                                        false,
                                        0,
                                        0,
                                        0,
                                        0,
                                        mutableMapOf(),
                                        Constants.STATUS_IN_PROGRESS

                                    )
                                    viewModel.useCases.addStudentTaskFsUseCase.execute(viewModel, newTask){}
                                    viewModel.useCases.updateLastTaskIdSfxFsUseCase.execute(viewModel.user.value.lastIdSfx, viewModel.user.value.fsId.value) {}
                                    viewModel.studentTasks.add(TaskInfo(newTask.id, newTask.taskTitle,  vocabularyTitles[selectedIndex.value], Constants.STATUS_IN_PROGRESS))
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                        selectedIndex.value = -1
                                        searchingValue.value = ""
                                        newTaskTitle.value = ""
                                    }
                                }
                            }
                        }
                    }

                } ) {
                Icon(Icons.Filled.Done, "Add new task")
            }
        }
    }
}

@Composable
private fun TitlesLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}

@Composable
private fun TitlesComponentList(
    titles: List<VocabularyTitle>,
    vocabulariesListState: LazyListState,
    selectedIndex: MutableState<Int>,
    viewModel: MainViewModel

){
    val itemsIds by viewModel.vocabularyTitlesIds.collectAsState()
    LazyColumn(
        state = vocabulariesListState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        itemsIndexed(viewModel.allVocabularyTitles) { index, item ->
           // VocabularyListItem(index, item.value, selectedIndex)

            ExpandableListItem(
                index = index,
                vcbTitle = item ,
                selectedIndex = selectedIndex,
                onClickItem = { viewModel.onVcbTitleItemClicked(index, item) },
                expanded = itemsIds.contains(index),
                viewModel = viewModel,
                lazyListState = vocabulariesListState
            )
        }
    }
}