package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class GetAllVocabularyTitlesFsUseCase() {

    fun execute(
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ) {
        val db = Firebase.firestore
        val titles = mutableListOf<VocabularyTitle>()
        db.collection(Constants.VOCABULARIES_MAIN_REF)
            .get()
            .addOnSuccessListener { result ->
                Log.d("getAllVocabularyTitlesFsUseCase", "invoked")

                result.forEachIndexed { index, doc ->
                    Firebase.firestore.collection(Constants.VOCABULARIES_MAIN_REF).document(doc.reference.id)
                        .collection(Constants.VOCABULARIES).get()
                        .addOnSuccessListener { docs ->
                            for (item in docs) {
                                val title = VocabularyTitle(item[Constants.VOCABULARY_TITLE].toString())
                                title.fsDocRefPath = item.reference.path
                                title.timestamp = item[Constants.TIMESTAMP].toString()
                                title.price = item[Constants.PRICE].toString().toInt()
                                titles.add(title)
                                Log.d("getAllVocabularyTitlesFsUseCase", "another title = ${title.value}")
                                Log.d("getAllVocabularyTitlesFsUseCase", "another title.fsDocRef = ${title.fsDocRefPath}")
                                Log.d("getAllVocabularyTitlesFsUseCase", "another title.timestamp = ${title.timestamp}")
                            }
                            if (index == result.size()-1) {
                                viewModel.allVocabularyTitles.clear()
                                viewModel.allVocabularyTitles += titles
                                onResponse()
                            }
                        }
                        .addOnFailureListener {
                            Log.d("getAllVocabularyTitlesFsUseCase", "reading titles failed, Error: ${it.message}")
                        }
                }
            }
            .addOnFailureListener {
                Log.d("getVocabularyTitles", "Error = ${it.message}")
            }
    }
}