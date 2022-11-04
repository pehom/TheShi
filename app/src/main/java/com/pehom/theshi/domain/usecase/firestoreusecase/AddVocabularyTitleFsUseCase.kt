package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.utils.Constants

class AddVocabularyTitleFsUseCase {
    fun execute(
        fsId: FsId,
        _newVocabularyTitle: VocabularyTitle,
        onResponse: ()->Unit
    ) {
        val newVocabularyTitle = hashMapOf(
            Constants.VOCABULARY_TITLE to _newVocabularyTitle.value
        )
        Firebase.firestore.collection(Constants.USERS).document(fsId.value)
            .collection(Constants.AVAILABLE_VOCABULARIES).add(newVocabularyTitle)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("addVocabularyTitleFsUseCase", "adding vocabularyTitle failed, Error: ${it.message}")
            }
    }
}