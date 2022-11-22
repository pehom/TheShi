package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.UserRoomItem
import com.pehom.theshi.utils.Constants

class ReadUserinfoByAuthIdFsUseCase {
    private val TAG = "ReadUserinfoByAuthIdFsUseCase"

    fun execute(
        password: String,
        authId: String?,
        onSuccess: (UserRoomItem?) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        if (authId != null){
            Firebase.firestore.collection(Constants.USERS).whereEqualTo(Constants.AUTH_ID, authId).get()
                .addOnSuccessListener {
                    val docs = it.documents
                    if (docs.size == 1){
                        val user = UserRoomItem(
                            docs[0][Constants.FS_ID].toString(),
                            docs[0][Constants.AUTH_ID].toString(),
                            docs[0][Constants.NAME].toString(),
                            docs[0][Constants.PHONE_NUMBER].toString(),
                            docs[0][Constants.EMAIL].toString(),
                            password,
                            docs[0][Constants.FUNDS].toString().toInt(),
                            docs[0][Constants.LAST_TASK_ID_SFX].toString().toInt()
                        )
                        onSuccess(user)
                    }
                    else {
                        onSuccess(null)
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "getting user from Fs failed, Error: ${it.message}")
                    onSuccess(null)
                }
        } else {
            Log.d(TAG, "authId = null")
            onSuccess(null)
        }

    }
}