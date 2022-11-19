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
    private val TAG ="ReadStudentTasksFsUseCase"
    fun execute(
        context: Context,
        viewModel: MainViewModel,
        onResponse: (MutableList<TaskInfo>) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")

        if (isNetworkAvailable()) {
            val tasks = mutableListOf<TaskInfo>()
            val studentFsId = viewModel.currentStudent.value.fsId.value
            Firebase.firestore.collection(Constants.USERS).document(studentFsId)
                .collection(Constants.TASKS_BY_MENTOR)
                .whereEqualTo(Constants.MENTOR_FS_ID, viewModel.user.value.fsId.value ).get()
                .addOnSuccessListener {docs->
                    docs.forEachIndexed(){index, doc ->
                        val details = doc[Constants.DETAILS] as Map<*,*>
                        val title = details[Constants.TASK_TITLE].toString()
                        val progress = details[Constants.PROGRESS].toString().toInt()
                        val taskId = details[Constants.TASK_ID].toString()
                        val vcbTitle = details[Constants.VOCABULARY_TITLE].toString()
                        val status = doc[Constants.STATUS].toString()
                        val task = TaskInfo(taskId,title,VocabularyTitle(vcbTitle), status)
                        task.progress = progress
                        Log.d(TAG, "task.title = ${task.title}")
                        tasks.add(task)
                        if (index == docs.size()-1) {
                            onResponse(tasks)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d("readStudentTasksFsUseCase", "reading tasks failed, Error: ${it.message}")
                }
        } else {
            Toast.makeText(context, context.getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show()
        }

    }
}