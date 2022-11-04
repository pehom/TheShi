package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.utils.Constants

class ReadVcbItemsByVcbDocRefFsUseCase {
    private val TAG = "ReadVcbItemsByVcbDocRef"
    fun execute(
        vcbDocRef: String,
       // onSuccess: (VocabularyTitle) -> Unit
        onSuccess: (MutableList<VocabularyItemScheme>) -> Unit
    )
  //  : LiveData<List<VocabularyItemScheme>>
    {
      //  val result = MutableLiveData<List<VocabularyItemScheme>>()
        val resultList = mutableListOf<VocabularyItemScheme>()
        var vcbTitle = VocabularyTitle("")
        Firebase.firestore.document(vcbDocRef).get()
            .addOnSuccessListener { doc ->
                vcbTitle.value = doc[Constants.VOCABULARY_TITLE].toString()
                vcbTitle.fsDocRefPath = vcbDocRef
                vcbTitle.timestamp = doc[Constants.TIMESTAMP].toString()
                vcbTitle.price = doc[Constants.PRICE].toString().toInt()
                Firebase.firestore.document(vcbDocRef).collection(Constants.ITEMS).get()
                    .addOnSuccessListener { docs ->
                        for (doc in docs) {
                            val vocabularyItemScheme = VocabularyItemScheme(
                                doc[Constants.ORIG].toString(),
                                doc[Constants.TRANS].toString(),
                                doc[Constants.IMG_URL].toString()
                            )
                            resultList.add(vocabularyItemScheme)
                        }
                      //  result.value = resultList
                        onSuccess(resultList)
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "reading vcbItems failed, Error: ${it.message}")
                    }
            }
            .addOnFailureListener{
                Log.d(TAG, "getting vcbTitle failed, Error: ${it.message}")
            }
    //    return result
    }
}