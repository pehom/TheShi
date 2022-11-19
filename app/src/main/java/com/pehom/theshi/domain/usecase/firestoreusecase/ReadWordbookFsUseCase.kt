package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadWordbookFsUseCase {
    private val TAG = "ReadWordbookFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        onSuccess: (List<String>) -> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        val fsDocRefPaths = mutableListOf<String>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value).collection(Constants.WORDBOOK).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    fsDocRefPaths.add(doc[Constants.VOCABULARY_FS_DOC_REF_PATH].toString())
                }
                onSuccess(fsDocRefPaths)
            }
            .addOnFailureListener {
                Log.d("readWordbookFsUseCase", "reading wordbook failed, Error: ${it.message}")
            }
    }
}