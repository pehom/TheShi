package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class SetTaskProgressFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ) {
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.TASKS).document(viewModel.currentTask.value.id)
            .update(Constants.PROGRESS, viewModel.currentTask.value.progress)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("setTaskProgressFsUseCase", "updating progress failed, Error: ${it.message}")
            }
    }
}