package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.utils.Constants

class ReadVcbTitleByFsDocRefPathFsUseCase {
    private val TAG = "ReadVcbTitleByFsDocRefPath"
    fun execute(
        vcbFsDocRefPath: String,
        onSuccess: (VocabularyTitle) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.document(vcbFsDocRefPath).get()
            .addOnSuccessListener {
                val title = it[Constants.VOCABULARY_TITLE].toString()
                val timeStamp = it[Constants.TIMESTAMP].toString()
                val price = it[Constants.PRICE].toString().toInt()
                val result = VocabularyTitle(title,vcbFsDocRefPath,timeStamp,price)
                Log.d(TAG, "result = $result")
                onSuccess(result)
            }
            .addOnFailureListener{
                Log.d(TAG, "getting vcbTitle failed, Error: ${it.message}")
            }

    }
}