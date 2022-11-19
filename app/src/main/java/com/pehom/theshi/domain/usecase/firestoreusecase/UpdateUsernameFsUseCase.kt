package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.utils.Constants

class UpdateUsernameFsUseCase {
    private val TAG = "UpdateUserInfoFsUseCase"

    fun execute(
        userFsId: String,
        username: String,
        onSuccess: (Boolean) -> Unit
        ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(userFsId)
            .update(Constants.NAME, username )
            .addOnSuccessListener {
                onSuccess(true)
            }
            .addOnFailureListener {
                Log.d(TAG, "updating username failed, Error: ${it.message}")
                onSuccess(false)
            }
    }
}