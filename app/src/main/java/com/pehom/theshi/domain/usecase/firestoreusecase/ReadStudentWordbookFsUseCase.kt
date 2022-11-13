package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.utils.Constants

class ReadStudentWordbookFsUseCase {
    private val TAG = "ReadStudentWordbookFsUseCase"

    fun execute(
        studentFsID: String,
        onSuccess: (List<String>) -> Unit
    ) {
        val result = mutableListOf<String>()
        Firebase.firestore.collection(Constants.USERS).document(studentFsID).collection(Constants.WORDBOOK).get()
            .addOnSuccessListener {docs ->
                for (doc in docs) {
                    result.add(doc[Constants.VOCABULARY_TITLE].toString())
                }
                onSuccess(result)
            }
            .addOnFailureListener {
                Log.d(TAG, "reading student's wordbook failed, Error: ${it.message}")
            }
    }
}