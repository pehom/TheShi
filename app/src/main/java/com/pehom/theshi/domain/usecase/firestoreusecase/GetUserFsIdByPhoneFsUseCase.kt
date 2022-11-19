package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.utils.Constants


class GetUserFsIdByPhoneFsUseCase {
    private val TAG = "GetUserFsIdByPhoneFsUseCase"
    fun execute (
         phone: String,
         onResponse: (FsId) -> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        var fsId = FsId("")
        Firebase.firestore.collection(Constants.USERS).whereEqualTo(Constants.PHONE_NUMBER, phone).get()
            .addOnSuccessListener { docs ->
                if (docs.size() == 1) {
                    for (doc in docs) {
                        fsId = FsId(doc[Constants.FS_ID].toString())
                    }
                }
                else
                    Log.d("getUserFsIdByPhoneFsUseCase", "getting userFsId failed, Error: wrong result of query")
                onResponse(fsId)
            }
            .addOnFailureListener {
                Log.d("getUserFsIdByPhoneFsUseCase", "getting FsId failed, Error: ${it.message}")
            }
    }
}