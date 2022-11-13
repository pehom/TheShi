package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadAllUserTasksFsUseCase {
    private val TAG ="readAllUserTasksFsUseCase"
    fun execute(
        viewModel: MainViewModel
    ): LiveData<List<TaskRoomItem>>
    {
        val result = MutableLiveData<List<TaskRoomItem>>()
        val resultList = mutableListOf<TaskRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.TASKS_BY_USER).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    val details = doc.data["details"] as MutableMap<*,*>
                    Log.d("mmm", "details = $details")
                    val taskId = details[Constants.TASK_ID].toString()
                    Log.d("mmm", "taskId = $taskId")
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
                    resultList.add(taskRoomItem)
                }
                Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                    .collection(Constants.TASKS_BY_MENTOR).get()
                    .addOnSuccessListener { docs ->
                        for (doc in docs) {
                            val details = doc.data["details"] as MutableMap<*,*>
                            Log.d("mmm", "details = $details")
                            val taskId = details[Constants.TASK_ID].toString()
                            Log.d("mmm", "taskId = $taskId")
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
                            resultList.add(taskRoomItem)
                        }

                        result.value = resultList
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "reading tasks by mentor failed, Error: ${it.message}")
                    }

            }.addOnFailureListener{
                Log.d(TAG, "reading  tasks by user failed, Error: ${it.message}")
            }
        return result
    }
}