package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadNewAvailableVocabulariesFsUseCase {
    private val TAG = "ReadNewAvailableVocabulariesFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: (List<AvailableVocabularyRoomItem>) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val resultList = mutableListOf<AvailableVocabularyRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.AVAILABLE_VOCABULARIES).get()
            .addOnSuccessListener {docs ->
                docs.forEachIndexed{index, doc ->
                    viewModel.useCases.readVcbTitleByFsDocRefPathFsUseCase.
                    execute(doc[Constants.VOCABULARY_FS_DOC_REF_PATH].toString()){vcbTitle ->
                        resultList.add(
                            AvailableVocabularyRoomItem(
                                null,
                                vcbTitle.fsDocRefPath,
                                vcbTitle.timestamp,
                                vcbTitle.value,
                                vcbTitle.price,
                                viewModel.user.value.fsId.value
                            )
                        )
                        if (index == docs.size()-1){
                            onSuccess(resultList)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "getting available vocabularies failed, Error: ${it.message}")
            }
    }
}