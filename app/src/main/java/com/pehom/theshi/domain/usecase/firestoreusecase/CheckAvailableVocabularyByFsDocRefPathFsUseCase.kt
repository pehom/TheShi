package com.pehom.theshi.domain.usecase.firestoreusecase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

class CheckAvailableVocabularyByFsDocRefPathFsUseCase {
    private val TAG = "GetAvailableVocabularyByFsDocRefPathFsUseCase"
    fun execute(
        user: User,
        fsDocRefPath: String,
        onSuccess: (Boolean) -> Unit
    ){

        Firebase.firestore.collection(Constants.USERS).document(user.fsId.value)
            .collection(Constants.AVAILABLE_VOCABULARIES)
            .whereEqualTo(Constants.VOCABULARY_FS_DOC_REF_PATH, fsDocRefPath).get()
            .addOnSuccessListener { docs ->
                if (docs.size() == 1) {
                    onSuccess(true)
                } else
                    onSuccess(false)
            }
    }
}