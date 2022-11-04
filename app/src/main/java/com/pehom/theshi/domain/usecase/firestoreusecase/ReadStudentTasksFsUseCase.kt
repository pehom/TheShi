package com.pehom.theshi.domain.usecase.firestoreusecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.TaskInfo
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable

class ReadStudentTasksFsUseCase {
    fun execute(
        context: Context,
        viewModel: MainViewModel,
        onResponse: (MutableList<TaskInfo>) -> Unit
    ){
        if (isNetworkAvailable()) {
            val tasks = mutableListOf<TaskInfo>()
            val studentFsId = viewModel.studentFsId.value
            Firebase.firestore.collection(Constants.USERS).document(studentFsId)
                .collection(Constants.TASKS).get()
                .addOnSuccessListener { docs ->
                    for (doc in docs) {
                        val title = doc[Constants.TASK_TITLE].toString()
                        val vocabularyTitle = VocabularyTitle(doc[Constants.VOCABULARY_TITLE].toString())
                        val fsDocRef = doc[Constants.VOCABULARY_FS_DOC_REF_PATH].toString()

                        val vcbTimeStamp = doc[Constants.VCB_TIMESTAMP].toString()
                        Log.d("ReadStudentTasksFsUseCase", "fsDocRef = $fsDocRef")
                        vocabularyTitle.fsDocRefPath = fsDocRef
                        vocabularyTitle.timestamp = vcbTimeStamp
                        val taskId = doc[Constants.TASK_ID].toString()
                        val progress = doc[Constants.PROGRESS].toString().toInt()
                        val currentTaskItem = doc[Constants.CURRENT_TASK_ITEM].toString().toInt()
                        val currentTestItem = doc[Constants.CURRENT_TEST_ITEM].toString().toInt()
                        val task = TaskInfo(taskId,title,vocabularyTitle)
                        task.progress = progress
                        task.currentTaskItem = currentTaskItem
                        task.currentTestItem = currentTestItem
                        tasks.add(task)
                    }
                    onResponse(tasks)
                }
                .addOnFailureListener {
                    Log.d("readStudentTasksFsUseCase", "reading tasks failed, Error: ${it.message}")
                }
        } else {
            Toast.makeText(context, context.getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show()
        }

    }
}