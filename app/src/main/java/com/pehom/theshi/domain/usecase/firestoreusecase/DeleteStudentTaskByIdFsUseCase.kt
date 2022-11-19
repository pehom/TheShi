package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.utils.Constants

class DeleteStudentTaskByIdFsUseCase {
    private val TAG = "DeleteStudentTaskByIdFsUseCase"

    fun execute(
        studentFsId: String,
        taskId: String
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(studentFsId)
            .collection(Constants.TASKS_BY_MENTOR).document(taskId).get()
            .addOnSuccessListener {
                if (it.exists()){
                    it.reference.delete()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "checking if task exists failed, Error: ${it.message}")
            }
    }
}