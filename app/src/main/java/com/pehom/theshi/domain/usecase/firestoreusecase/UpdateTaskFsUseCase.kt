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
        val details = hashMapOf(
            Constants.CURRENT_TASK_ITEM to taskRoomItem.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to taskRoomItem.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to taskRoomItem.currentLearningItem,
            Constants.PROGRESS to taskRoomItem.progress,
            Constants.IS_AVAILABLE to taskRoomItem.isAvailable,
            Constants.STUDENT_FS_ID to taskRoomItem.studentFsId,
            Constants.TASK_ID to taskRoomItem.id,
            Constants.TASK_TITLE to taskRoomItem.taskTitle,
            Constants.VOCABULARY_FS_DOC_REF_PATH to taskRoomItem.vcbFsDocRefPath,
            Constants.WRONG_TEST_ANSWERS to stringMap
        )
        val data = hashMapOf(
            Constants.DETAILS to details,
            Constants.MENTOR_FS_ID to taskRoomItem.mentorFsId,
            Constants.STATUS to taskRoomItem.status
        )
        Log.d("updateTaskFsUseCase", "data: $data")

        if (taskRoomItem.mentorFsId == viewModel.user.value.fsId.value){
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .collection(Constants.TASKS_BY_USER).document(viewModel.currentTaskRoomItem.value.id)
                .set(data)
                .addOnSuccessListener {
                    onResponse()
                }
                .addOnFailureListener {
                    Log.d("updateTaskFsUseCase", "updating task failed, Error: ${it.message}")
                }
        } else {
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .collection(Constants.TASKS_BY_MENTOR).document(viewModel.currentTaskRoomItem.value.id)
                .update(Constants.DETAILS, data)
                .addOnSuccessListener {
                    onResponse()
                }
                .addOnFailureListener {
                    Log.d("updateTaskFsUseCase", "updating task failed, Error: ${it.message}")
                }
        }
    }
}

