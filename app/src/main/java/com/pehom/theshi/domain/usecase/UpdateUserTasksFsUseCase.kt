package com.pehom.theshi.domain.usecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.tasksToString

class UpdateUserTasksFsUseCase {
    private val db = Firebase.firestore
    fun execute(viewModel: MainViewModel, onResponse: () -> Unit) {
        db.collection("Users").document(viewModel.user.fsId)
            .update(Constants.TASKS, tasksToString(viewModel.tasksInfo))
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("updateTasksFsUseCase", "Update tasks failed Error: ${it.message}")
            }
    }
}