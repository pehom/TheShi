package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class DeleteUserTaskByIdFsUseCase {
    private val TAG = "DeleteTaskByIdFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        taskId: String,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.TASKS_BY_USER).document(taskId).delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                Log.d(TAG, "deleting task failed, Error: ${it.message}")
            }
    }
}