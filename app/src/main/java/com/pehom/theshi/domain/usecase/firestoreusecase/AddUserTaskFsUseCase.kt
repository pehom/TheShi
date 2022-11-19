package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AddUserTaskFsUseCase {
    private val db = Firebase.firestore
    private val TAG = "AddUserTaskFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        _newTask: TaskRoomItem,
        onResponse: () -> Unit)
    {
        Log.d(TAG, "$TAG invoked")
        val details = hashMapOf(
            Constants.TASK_ID to _newTask.id,
            Constants.TASK_TITLE to _newTask.taskTitle,
            Constants.STUDENT_FS_ID to _newTask.studentFsId,
            Constants.VOCABULARY_FS_DOC_REF_PATH to _newTask.vcbFsDocRefPath,
            Constants.VOCABULARY_TITLE to _newTask.vcbTitle,
            Constants.IS_AVAILABLE to _newTask.isAvailable,
            Constants.PROGRESS to _newTask.progress,
            Constants.CURRENT_TASK_ITEM to _newTask.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to _newTask.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to _newTask.currentLearningItem,
            Constants.WRONG_TEST_ANSWERS to _newTask.wrongTestAnswers,
        )
        val data = hashMapOf(
            Constants.DETAILS to details,
            Constants.MENTOR_FS_ID to _newTask.mentorFsId,
            Constants.STATUS to _newTask.status
        )
        db.collection("Users").document(viewModel.user.value.fsId.value)
            .collection(Constants.TASKS_BY_USER).document(_newTask.id).set(data)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("AddUserTaskFsUseCase", "Adding task failed, Error: ${it.message}")
            }
    }
}