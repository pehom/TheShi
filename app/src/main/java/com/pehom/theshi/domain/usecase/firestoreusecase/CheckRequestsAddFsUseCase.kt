package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class CheckRequestsAddFsUseCase {
    private val TAG = "CheckRequestsAddFsUseCase"

    fun execute(
        viewModel: MainViewModel
    ){
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {

                }
            }
            .addOnFailureListener {
                Log.d(TAG, "getting requests failed, Error: ${it.message}")
            }
    }
}