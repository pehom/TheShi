package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable

class CheckIsAdminFsUseCase {
    private val TAG = "CheckIsAdminFsUseCase"

    fun execute(
        user: User,
        onResult: (Boolean) -> Unit
    ) {
        if (isNetworkAvailable()) {
            Firebase.firestore.collection(Constants.ADMINS).document(user.fsId.value).get()
                .addOnSuccessListener {
                    Log.d(TAG, "doc = ${it.data}")
                    if (it.exists()) {
                        Log.d(TAG, "check isAdmin result: true")
                        onResult(true)
                    } else{
                        Log.d(TAG, "check isAdmin result: false")
                        onResult(false)
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "check isAdmin failed, Error: ${it.message}")
                    onResult(false)
                }
        } else{
            Log.d(TAG, "check isAdmin result: network is not available")
            onResult(false)
        }
    }
}