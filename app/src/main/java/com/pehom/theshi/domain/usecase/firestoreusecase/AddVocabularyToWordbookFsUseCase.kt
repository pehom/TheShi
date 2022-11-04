package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable

class AddVocabularyToWordbookFsUseCase {
    private val TAG = "AddVocabularyToWordbookFsUseCase"
    fun execute(vocabulary: Vocabulary,
                fsId: FsId,
                onSuccess: ()-> Unit) {
            val wordbookRef = Firebase.firestore.collection(Constants.USERS).document(fsId.value)
                .collection(Constants.WORDBOOK).document(vocabulary.title.value)
         //   val isNetworkAvailable = true  //TODO Network status checking needed
            val data = hashMapOf(
                Constants.VOCABULARY_FS_DOC_REF_PATH to vocabulary.title.fsDocRefPath
            )
            if (isNetworkAvailable()) {
                wordbookRef.set(data)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "adding vocabulary to wordbookFs failed, Error: ${it.message}")
                    }
            }
        }
}