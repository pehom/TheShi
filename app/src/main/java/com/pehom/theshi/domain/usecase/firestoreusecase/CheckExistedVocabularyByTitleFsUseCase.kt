package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.utils.Constants

class CheckExistedVocabularyByTitleAndLevelFsUseCase {
    private val TAG = "CheckExistedVocabularyByTitleFsUseCase"

    fun execute(
        vcbTitle: String,
        vcbLevel: String,
        onResult: (Boolean) -> Unit
    ){
        Firebase.firestore.collection(Constants.VOCABULARIES_MAIN_REF).document(vcbLevel)
            .collection(Constants.VOCABULARIES).document(vcbTitle).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    Log.d(TAG, "vcb: $vcbTitle already exists ")
                    onResult(true)
                }
                else {
                    Log.d(TAG, "vcb: $vcbTitle does not exist")
                    onResult(false)
                }

            }
            .addOnFailureListener {
                Log.d(TAG, "checking existed vocabulary by title and level failed, Error: ${it.message}")
            }
    }
}