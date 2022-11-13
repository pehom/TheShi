package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AddStudentTaskFsUseCase {
    private val TAG = "AddStudentTaskFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        _newTask:TaskRoomItem,
        onSuccess: () -> Unit
    ) {
        val db = Firebase.firestore
        val newTask = hashMapOf(
            Constants.TASK_ID to _newTask.id,
            Constants.TASK_TITLE to _newTask.taskTitle,
            Constants.STUDENT_FS_ID to _newTask.studentFsId,
            Constants.VOCABULARY_FS_DOC_REF_PATH to _newTask.vcbFsDocRefPath,
            Constants.IS_AVAILABLE to _newTask.isAvailable,
            Constants.PROGRESS to _newTask.progress,
            Constants.CURRENT_TASK_ITEM to _newTask.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to _newTask.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to _newTask.currentLearningItem,
            Constants.WRONG_TEST_ANSWERS to _newTask.wrongTestAnswers,
            Constants.SYNC_COUNT to _newTask.syncCount,
            Constants.VOCABULARY_TITLE to _newTask.vcbTitle,
            Constants.IS_CHECKED to false
        )
        val details = hashMapOf(
            Constants.DETAILS to newTask,
            Constants.MENTOR_FS_ID to _newTask.mentorFsId
            )
        db.collection(Constants.USERS).document(viewModel.currentStudent.value.fsId.value)
            .collection(Constants.TASKS_BY_MENTOR).document(_newTask.id).set(details)
            .addOnSuccessListener {
                db.collection(Constants.USERS).document(viewModel.currentStudent.value.fsId.value)
                    .update(Constants.TASKS_BY_MENTOR_GOT_CHANGES, true)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "updating tasks by mentor got changes failed, Error: ${it.message}" )
                    }
            }
            .addOnFailureListener {
                Log.d("addStudentTaskFsUseCase", "Adding student task failed,  Error: ${it.message}")
            }
    }
}