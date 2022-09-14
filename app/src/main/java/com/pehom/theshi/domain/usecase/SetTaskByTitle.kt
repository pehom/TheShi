package com.pehom.theshi.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel


class SetTaskByTitle  {
    private val dbRef = Firebase.firestore.collection("Vocabularies")

    fun execute(viewModel: MainViewModel) {
        val words = mutableListOf<VocabularyItemScheme>()
        val taskInfo = viewModel.tasksInfo[viewModel.currentTaskNumber.value]
        val taskTitle = taskInfo.title
        val taskId = taskInfo.id
        val vocabularyTitle =  viewModel.tasksInfo[viewModel.currentTaskNumber.value].vocabularyTitle.title
        val resultVocabulary = Vocabulary(vocabularyTitle,mutableListOf<VocabularyItemScheme>())
        Log.d("setTaskByTitle", "voc.title = ${resultVocabulary.title}")
        dbRef.document(vocabularyTitle).collection("Items").get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                     words.add(
                         VocabularyItemScheme(doc["orig"].toString(),
                                             doc["trans"].toString(),
                                             doc["imgUrl"].toString()))
                }
                resultVocabulary.items += words
                viewModel.currentTask.value = Task(taskId, taskTitle, resultVocabulary)
                viewModel.currentTask.value.vocabulary = resultVocabulary
                viewModel.currentTask.value.setReadyForTask(taskInfo.currentTaskItem.value)
                viewModel.currentTask.value.setReadyForTest(taskInfo.currentTestItem.value)

                viewModel.screenState.value = viewModel.MODE_TASK_SCREEN
            }
            .addOnFailureListener { e ->
                Log.d("getVocabularyByTitle", "Error: ${e.message}")
            }

    }
}