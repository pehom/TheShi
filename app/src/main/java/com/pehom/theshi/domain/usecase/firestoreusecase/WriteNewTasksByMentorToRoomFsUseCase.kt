package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteNewTasksByMentorToRoomFsUseCase {
    private val TAG = "WriteNewTasksByMentorFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG .execute() invoked")
        if (isNetworkAvailable()){
            viewModel.useCases.readNewUserTasksByMentorFsUseCase.execute(viewModel.user.value){
                if (it.isNotEmpty()) {
                    Log.d(TAG, "newTaskRoomList.size  = ${it.size}")
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        it.forEachIndexed() {index, taskRoomItem ->
                            Constants.REPOSITORY.createTaskRoomItem(taskRoomItem){
                                //  docRef.update(Constants.IS_CHECKED, true)
                                if (index == it.size-1) {
                                    onSuccess()
                                }
                            }
                        }
                    }
                }else {
                    Log.d(TAG, "newTaskRoomList.size  = 0")
                    onSuccess()
                }
            }
        } else {
            onSuccess()
        }

        /*if (viewModel.user.value.fsId.value != "") {
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()){
                        if (doc[Constants.TASKS_BY_MENTOR_GOT_CHANGES].toString().toBoolean()){
                            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                                .collection(Constants.TASKS_BY_MENTOR).whereEqualTo(Constants.IS_CHECKED, false).get()
                                .addOnSuccessListener { docs ->
                                    for (doc in docs){
                                        val docRef = doc.reference
                                        val details = doc.data?.get("details") as MutableMap<*,*>
                                        Log.d(TAG, "details = $details")
                                        val taskId = details[Constants.TASK_ID].toString()
                                        Log.d(TAG, "taskId = $taskId")
                                        val taskTitle = details[Constants.TASK_TITLE].toString()
                                        val studentFsId = details[Constants.STUDENT_FS_ID].toString()
                                        val mentorFsId = details[Constants.MENTOR_FS_ID].toString()
                                        val vcbFsDocRefPath = details[Constants.VOCABULARY_FS_DOC_REF_PATH].toString()
                                        val vcbTitle = details[Constants.VOCABULARY_TITLE].toString()
                                        val isAvailable = details[Constants.IS_AVAILABLE].toString().toBoolean()
                                        val progress = details[Constants.PROGRESS].toString().toInt()
                                        val currentTaskItem = details[Constants.CURRENT_TASK_ITEM].toString().toInt()
                                        val currentTestItem = details[Constants.CURRENT_TEST_ITEM].toString().toInt()
                                        val currentLearningItem = details[Constants.CURRENT_LEARNING_ITEM].toString().toInt()
                                        val wrongTestAnswers = details[Constants.WRONG_TEST_ANSWERS] as MutableMap<Int, String>
                                        val syncCount = details[Constants.SYNC_COUNT].toString().toInt()
                                        val taskRoomItem = TaskRoomItem(
                                            taskId,
                                            mentorFsId,
                                            studentFsId,
                                            taskTitle,
                                            vcbFsDocRefPath,
                                            vcbTitle,
                                            isAvailable,
                                            progress,
                                            currentTaskItem,
                                            currentTestItem,
                                            currentLearningItem,
                                            wrongTestAnswers,
                                            syncCount
                                        )
                                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                                            Constants.REPOSITORY.createTaskRoomItem(taskRoomItem){
                                                docRef.update(Constants.IS_CHECKED, true)
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
        }*/
    }
}