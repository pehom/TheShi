package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class UpdateLastTaskIdSfxFsUseCase {
    private val TAG = "UpdateLastTaskIdSfxFsUseCase"
    fun execute(
        lastTaskIdSfx: Int,
        viewModel: MainViewModel,
        onResponse: ()-> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .update(Constants.LAST_TASK_ID_SFX, lastTaskIdSfx)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("updateLastTaskIdSfxFsUseCase", "updating lastIdSfx failed, Error: ${it.message} ")
            }
    }
}