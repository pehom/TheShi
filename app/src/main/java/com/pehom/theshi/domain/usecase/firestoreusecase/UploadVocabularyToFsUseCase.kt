package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.VocabularyUploadToFs
import com.pehom.theshi.utils.Constants

class UploadVocabularyToFsUseCase {
    private val TAG = "UploadVocabularyToFsUseCase"

    fun execute(

        vcbUpload: VocabularyUploadToFs,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val vcbData = hashMapOf(
            Constants.VOCABULARY_TITLE to vcbUpload.vcb.title.value,
            Constants.PRICE to vcbUpload.price,
            Constants.TIMESTAMP to FieldValue.serverTimestamp()
        )
        Firebase.firestore.collection(Constants.VOCABULARIES_MAIN_REF).document(vcbUpload.level)
            .collection(Constants.VOCABULARIES).document(vcbUpload.vcb.title.value)
            .set(vcbData )
            .addOnSuccessListener{
                vcbUpload.vcb.items.forEachIndexed(){index, item ->
                    val data = hashMapOf (
                        Constants.ORIG to item.orig,
                        Constants.TRANS to item.trans,
                        Constants.IMG_URL to item.imgUrl
                    )
                    Firebase.firestore.collection(Constants.VOCABULARIES_MAIN_REF).document(vcbUpload.level)
                        .collection(Constants.VOCABULARIES).document(vcbUpload.vcb.title.value)
                        .collection(Constants.ITEMS).document(item.orig).set(data)
                        .addOnSuccessListener {
                            if ( index == vcbUpload.vcb.items.size-1) {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener{
                            Log.d(TAG, "Uploading vocabulary to Fs failed, Error: ${it.message}")
                        }
                }
            }
            .addOnFailureListener{
                Log.d(TAG, "Uploading vocabulary to Fs failed, Error: ${it.message}")
            }
    }
}