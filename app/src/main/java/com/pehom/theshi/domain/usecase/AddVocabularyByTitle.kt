package com.pehom.theshi.domain.usecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme


class AddVocabularyByTitle  {
    private val dbRef = Firebase.firestore.collection("Vocabularies")

    fun execute(
        title: String,
        tasks: MutableList<Task>
                ) {
        val words = mutableListOf<VocabularyItemScheme>()
        val resultVocabulary = Vocabulary(title, mutableListOf<VocabularyItemScheme>())
        dbRef.document(title).collection("Items").get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                     words.add(
                         VocabularyItemScheme(doc["orig"].toString(),
                                             doc["trans"].toString(),
                                             doc["imgUrl"].toString()))
                }
                resultVocabulary.items += words
                tasks.add(Task(title, resultVocabulary))
            }
            .addOnFailureListener { e ->
                Log.d("getVocabularyByTitle", "Error: ${e.message}")
            }

    }
}