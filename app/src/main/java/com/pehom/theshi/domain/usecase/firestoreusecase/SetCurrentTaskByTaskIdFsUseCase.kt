package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class SetCurrentTaskByTaskIdFsUseCase {
    private val TAG = "SetCurrentTaskByTaskIdFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        vocabularyTitle: VocabularyTitle,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val currentTaskRoomItem = viewModel.currentTaskRoomItem.value
        val taskId = currentTaskRoomItem.id
        val taskTitle = currentTaskRoomItem.taskTitle
        val vcbFsDocRefPath = currentTaskRoomItem.vcbFsDocRefPath
        val vocabulary = Vocabulary(vocabularyTitle, mutableListOf())
        Firebase.firestore.document(vcbFsDocRefPath).collection(Constants.ITEMS).get()
            .addOnSuccessListener {docs ->
                for (doc in docs) {
                    val orig = doc[Constants.ORIG].toString()
                    val trans = doc[Constants.TRANS].toString()
                    val imgUrl = doc[Constants.IMG_URL].toString()
                    val vocabularyItemScheme = VocabularyItemScheme(orig, trans, imgUrl)
                    vocabulary.items.add(vocabularyItemScheme)
                }
                viewModel.currentTask.value = Task(taskId,taskTitle,vocabulary)
                onSuccess()
            }
            .addOnFailureListener {
                Log.d(TAG, "Setting task failed, Error: ${it.message}")
            }
    }
}