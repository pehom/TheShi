package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class UpdateTaskFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        taskRoomItem: TaskRoomItem,
        onResponse: ()-> Unit
    ){
        val stringMap =  mutableMapOf<String,String>()
        taskRoomItem.wrongTestAnswers.keys.forEach() {
            stringMap[it.toString()] = taskRoomItem.wrongTestAnswers[it].toString()
        }

        val data = hashMapOf(
            Constants.CURRENT_TASK_ITEM to taskRoomItem.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to taskRoomItem.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to taskRoomItem.currentLearningItem,
            Constants.PROGRESS to taskRoomItem.progress,
            Constants.IS_AVAILABLE to taskRoomItem.isAvailable,
            Constants.MENTOR_FS_ID to taskRoomItem.mentorFsId,
            Constants.STUDENT_FS_ID to taskRoomItem.studentFsId,
            Constants.TASK_ID to taskRoomItem.id,
            Constants.TASK_TITLE to taskRoomItem.taskTitle,
            Constants.VOCABULARY_FS_DOC_REF_PATH to taskRoomItem.vcbFsDocRefPath,
            Constants.WRONG_TEST_ANSWERS to stringMap,
            Constants.SYNC_COUNT to taskRoomItem.syncCount
        )
        Log.d("updateTaskFsUseCase", "data: $data")

        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.TASKS).document(viewModel.currentTaskRoomItem.value.id)
            .update(Constants.DETAILS, data)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("updateTaskFsUseCase", "updating task failed, Error: ${it.message}")
            }
    }
}

