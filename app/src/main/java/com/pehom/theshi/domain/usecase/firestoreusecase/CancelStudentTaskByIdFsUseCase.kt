package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.TaskInfo
import com.pehom.theshi.utils.Constants

class CancelStudentTaskByIdFsUseCase {
    private val TAG = "CancelStudentTaskByIdFsUseCase"

    fun execute(
        studentFsId: String,
        taskId: String,
        onSuccess: () -> Unit
    ){
        Firebase.firestore.collection(Constants.USERS).document(studentFsId).collection(Constants.TASKS_BY_MENTOR)
            .document(taskId).update(Constants.STATUS, Constants.STATUS_CANCELLED)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                Log.d(TAG, "updating task failed, Error: ${it.message}")
            }
    }
}