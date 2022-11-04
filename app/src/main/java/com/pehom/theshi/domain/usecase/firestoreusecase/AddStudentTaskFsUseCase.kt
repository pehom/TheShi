package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AddStudentTaskFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        _newTask:TaskRoomItem,
        onResponse: () -> Unit
    ) {
        val db = Firebase.firestore
        val newTask = hashMapOf(
            Constants.TASK_ID to _newTask.id,
            Constants.TASK_TITLE to _newTask.taskTitle,
            Constants.STUDENT_FS_ID to _newTask.studentFsId,
            Constants.MENTOR_FS_ID to _newTask.mentorFsId,
          //  Constants.VOCABULARY_ID to _newTask.vcbId,
            Constants.VOCABULARY_FS_DOC_REF_PATH to _newTask.vcbFsDocRefPath,
            Constants.IS_AVAILABLE to _newTask.isAvailable,
            Constants.PROGRESS to _newTask.progress,
            Constants.CURRENT_TASK_ITEM to _newTask.currentTaskItem,
            Constants.CURRENT_TEST_ITEM to _newTask.currentTestItem,
            Constants.CURRENT_LEARNING_ITEM to _newTask.currentLearningItem,
            Constants.WRONG_TEST_ANSWERS to _newTask.wrongTestAnswers

        )
        val details = hashMapOf(Constants.DETAILS to newTask)
        db.collection(Constants.USERS).document(viewModel.studentFsId.value)
            .collection(Constants.TASKS).document(_newTask.id).set(details)

        Log.d("vvv", "student fsId = ${viewModel.studentFsId.value}")

        db.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.STUDENTS).document(viewModel.studentFsId.value)
                .collection(Constants.TASKS).document(_newTask.id).set(details)
                    .addOnSuccessListener {
                        onResponse()
                    }
                    .addOnFailureListener {
                        Log.d("addStudentTaskFsUseCase", "Adding student task failed,  Error: ${it.message}")
                    }
    }
}