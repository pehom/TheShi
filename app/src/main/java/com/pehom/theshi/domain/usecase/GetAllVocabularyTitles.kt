package com.pehom.theshi.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.VocabularyTitle

class GetAllVocabularyTitles(val data: MutableList<VocabularyTitle>) {
    private val titles = mutableListOf<VocabularyTitle>()

    fun execute() {
        val db = Firebase.firestore

        db.collection("Vocabularies")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    titles.add(doc.toObject(VocabularyTitle::class.java))
                    Log.d("getVocabularyTitles", "data = ${doc.toObject(VocabularyTitle::class.java).title}")
                }
                data.clear()
                data += titles
            }
            .addOnFailureListener {
                Log.d("getVocabularyTitles", "Error = ${it.message}")
            }
    }
}