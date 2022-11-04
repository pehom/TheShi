package com.pehom.theshi.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.WordbookRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawerUserProfile(viewModel: MainViewModel, scaffoldState: ScaffoldState, auth: FirebaseAuth) {

    val TAG = "DrawerUserProfile"
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val availableWordsRoomItems = Constants.REPOSITORY
        .readAllAvailableWordsRoomItem
        .observeAsState(listOf()).value
    var wordbookRoomItems = remember { mutableStateListOf(WordbookRoomItem(null,"","")) }
    LaunchedEffect(key1 = null, block ={
        wordbookRoomItems.clear()
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            wordbookRoomItems += Constants.REPOSITORY.readWordbookRoomItemsByUserFsId(viewModel.user.value.fsId.value)
        }
    } )
    val context = LocalContext.current
    val tasks = Constants.REPOSITORY.readTaskRoomItemsByFsId(viewModel.user.value.fsId.value).observeAsState(
        listOf()
    ).value
    val availableVocabularyRoomItems = Constants.REPOSITORY.
        readAvailableVocabularyRoomItemsByUserFsId(viewModel.user.value.fsId.value).observeAsState(
        listOf()).value
   /* val availableVocabularyRoomItem = Constants.REPOSITORY
        .readAvailableVocabularyRoomItemByVcbDocRefPath("4i4", viewModel.user.value.fsId.value)
        .observeAsState(AvailableVocabularyRoomItem()).value*/

    Column(
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {

       OutlinedTextField(
           modifier = Modifier
               .fillMaxWidth()
               .padding(10.dp),
           value = viewModel.user.value.email,
           onValueChange = {},
           readOnly = true
       )
       Button(onClick = {
           viewModel.useCases.signOutUseCase.execute(viewModel, auth)
       }) {
           Text(text = stringResource(id = R.string.sign_out))
       }
       Button(onClick = {
           Log.d("userInfo", "vm.user fsID = ${viewModel.user.value.fsId} ")
           Log.d("userInfo", "vm.user phoneNumber = ${viewModel.user.value.phoneNumber} ")
           Log.d("userInfo", "vm.user email = ${viewModel.user.value.email} ")
           Log.d("userInfo", "vm.user funds = ${viewModel.user.value.funds.amount.value} ")
           viewModel.useCases.readFirestoreUserInfoUseCase.execute(viewModel.user.value.fsId.value, context) {
               Log.d("userInfo", "vm.user.tasks.size = ${viewModel.user.value.tasks.size}")
           }
       }) {
           Text(text = "user info")
       }

        Button(onClick = {
            Log.d("viewModelInfo", "viewModel.drawerType = ${viewModel.drawerType.value}")
            Log.d("viewModelInfo", "viewModel.isStudentProfileShown = ${viewModel.isStudentProfileShown.value}")
            Log.d("viewModelInfo", "viewModel.allVocabularyTitles.size = ${viewModel.allVocabularyTitles.size}")
            Log.d("viewModelInfo", "viewModel.currentTaskRoom = ${viewModel.currentTaskRoomItem.value}")
            Log.d("viewModelInfo", "viewModel.screenState = ${viewModel.screenState.value}")


        }) {
            Text(text = "viewModel info")
        }
        Button(onClick = {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                availableWordsRoomItems.forEach {
                    Constants.REPOSITORY.deleteAvailableWordsRoomItem(it)
                }
            }
        }) {
            Text(text = "clear available words ${availableWordsRoomItems.size}")
        }

        Button(onClick = {
            viewModel.screenState.value = viewModel.MODE_DEVELOPER_SCREEN
        }) {
            Text("developer screen")
        }

        Button(onClick = {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                wordbookRoomItems.forEachIndexed() { index, wordbookItem ->
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.deleteWordbookRoomItem(wordbookItem){}
                    }
                }
                availableWordsRoomItems.forEach {
                    Constants.REPOSITORY.deleteAvailableWordsRoomItem(it)
                }
            }
        }) {
            Text(text = "clear wordbookRoomDb (${wordbookRoomItems.size})")
        }
        Button(onClick = {
           viewModel.viewModelScope.launch(Dispatchers.IO) {
               tasks.forEachIndexed { index, taskRoomItem ->
                   Constants.REPOSITORY.deleteTaskRoomItem(taskRoomItem){}
               }
               Log.d("deleteTaskRoomItem", "allTaskRoomItems.size = ${Constants.REPOSITORY.readAllTaskRoomItems.value?.size}")
           }
        }) {
            Text(text = "clearTasksRoom (size=${tasks.size}")
        }
        Button(onClick = {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                availableVocabularyRoomItems.forEach {
                    Constants.REPOSITORY.deleteVocabularyRoomItem(it) {}
                }
            }
        }) {
            Text(text = "clear vcbRoom (size = ${availableVocabularyRoomItems.size})")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("watch docRef")
        }
        Button(onClick = {
            tasks.forEach {
                Log.d(TAG, "task = $it")
            }
        }) {
            Text(text = "get tasks RoomInfo")
        }
        Button(onClick = {
            viewModel.screenState.value = viewModel.MODE_ADMIN_SCREEN
        }) {
            Text(text = "admin screen")
        }

        Button(onClick = {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                Constants.REPOSITORY.readWordbookRoomItemByVcbDocRefPath("buba") {
                    Log.d("bbbb", "wordbookRoomItem = $it")
                }
            }

        }) {
            Text("check readWordbookItem")
        }

   }

}

