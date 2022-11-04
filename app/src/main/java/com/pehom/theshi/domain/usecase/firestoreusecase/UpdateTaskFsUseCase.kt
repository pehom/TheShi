package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class UpdateTaskFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        taskRoomItem: MutableState<TaskRoomItem>,
        onResponse: ()-> Unit
    ){
        val currentTask = taskRoomItem.value
        val data = hashMapOf(
            Constants.CURRENT_TASK_ITEM to currentTask.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to currentTask.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to currentTask.currentLearningItem,
            Constants.PROGRESS to currentTask.progress,
            Constants.IS_AVAILABLE to currentTask.isAvailable,
            Constants.MENTOR_FS_ID to currentTask.mentorFsId,
            Constants.STUDENT_FS_ID to currentTask.studentFsId,
            Constants.TASK_ID to currentTask.id,
            Constants.TASK_TITLE to currentTask.taskTitle,
            Constants.VOCABULARY_FS_DOC_REF_PATH to currentTask.vcbFsDocRefPath,
            Constants.WRONG_TEST_ANSWERS to currentTask.wrongTestAnswers
        )
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

