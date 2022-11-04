package com.pehom.theshi.presentation.screens.developerscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DeveloperScreen(
    viewModel: MainViewModel
) {
    Log.d("ppp", "DeveloperScreen is on")

    val tasks = Constants.REPOSITORY.readTaskRoomItemsByFsId(viewModel.user.value.fsId.value).observeAsState(listOf()).value
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column() {
            Button(onClick = {
                val taskRoomItem = tasks[0]
                val vocabularyItems = mutableListOf<VocabularyItemScheme>()
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    var vocabularyRoomItem = AvailableVocabularyRoomItem()
                    Constants.REPOSITORY.readAvailableVocabularyRoomItemByVcbDocRefPath(taskRoomItem.vcbFsDocRefPath, viewModel.user.value.fsId.value)
                        {
                            if (it != null) {
                                val vocabularyTitle = VocabularyTitle(
                                    it.vcbTitle,
                                    it.vcbDocRefPath,
                                    it.vcbTimeStamp,
                                    0
                                )
                                val docRef = Firebase.firestore.document(vocabularyRoomItem.vcbDocRefPath)
                                docRef.collection(Constants.ITEMS).get().addOnSuccessListener { docs ->
                                    for (doc in docs) {
                                        val imgUrl = doc[Constants.IMG_URL].toString()
                                        val orig = doc[Constants.ORIG].toString()
                                        val trans = doc[Constants.TRANS].toString()
                                        val vcbItem = VocabularyItemScheme(orig,trans,imgUrl)
                                        vocabularyItems.add(vcbItem)
                                    }
                                    val currentTask = Task(
                                        taskRoomItem.id,
                                        taskRoomItem.taskTitle,
                                        Vocabulary(vocabularyTitle, vocabularyItems)
                                    )
                                    viewModel.currentTask.value = currentTask
                                    viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
                                }
                            }
                        }
                }
            }) {
                Text("Set current task")
            }
            Button(onClick = {
                viewModel.screenState.value = viewModel.MODE_TEST_SCREEN
            }) {
                Text(text = "go test screen")
            }

            Button(onClick = {
                viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
            }) {
                Text(text = "go task screen")
            }
        }
    }

    /*ProgressButton(
        modifier = Modifier.size(60.dp)
        ,progress = 1f
    ) {

    }*/
   /* var initValue = 0.0f
    val infiniteTransition = rememberInfiniteTransition()
    val color = remember { mutableStateOf(Color.Red)}
    val changer = remember { mutableStateOf(false)}
    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = initValue,
        targetValue =1f,
        animationSpec = infiniteRepeatable(animation = tween(7000))
    )
    val lastValue = remember { progressAnimationValue}
    val count = remember { mutableStateOf(0)}
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            , contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                progress =progressAnimationValue,
                color = if (progressAnimationValue == 1f && changer.value) Color.Green
                else Color.Red,
                modifier = Modifier.size(60.dp)
                //modifier = Modifier.then(modifier)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            , contentAlignment = Alignment.Center) {
            Text("$lastValue",
                //"${(progressAnimationValue.absoluteValue*100).roundToInt()/100.0}",
                fontSize = 20.sp,
                modifier = Modifier.width(50.dp))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            , contentAlignment = Alignment.Center) {
            Button(onClick = {
             //   lastValue = progressAnimationValue
            }) {
                Text(text = "${lastValue}")
            }
        }

    }*/
}