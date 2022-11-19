package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.utils.Constants

class GetTaskByReferenceFsUseCase {
    private val TAG = "GetTaskByReferenceFsUseCase"

    fun execute(
        docRef: DocumentReference,
        onSuccess: (TaskRoomItem?) -> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        docRef.get()
            .addOnSuccessListener {
                val details = it.data?.get("details") as MutableMap<*,*>
                Log.d(TAG, "details = $details")
                val taskId = details[Constants.TASK_ID].toString()
                Log.d(TAG, "taskId = $taskId")
                val taskTitle = details[Constants.TASK_TITLE].toString()
                val studentFsId = details[Constants.STUDENT_FS_ID].toString()
                val mentorFsId = details[Constants.MENTOR_FS_ID].toString()
                val vcbFsDocRefPath = details[Constants.VOCABULARY_FS_DOC_REF_PATH].toString()
                val vcbTitle = details[Constants.VOCABULARY_TITLE].toString()
                val isAvailable = details[Constants.IS_AVAILABLE].toString().toBoolean()
                val progress = details[Constants.PROGRESS].toString().toInt()
                val currentTaskItem = details[Constants.CURRENT_TASK_ITEM].toString().toInt()
                val currentTestItem = details[Constants.CURRENT_TEST_ITEM].toString().toInt()
                val currentLearningItem = details[Constants.CURRENT_LEARNING_ITEM].toString().toInt()
                val wrongTestAnswers = details[Constants.WRONG_TEST_ANSWERS] as MutableMap<Int, String>
                val status = details[Constants.STATUS].toString()
                val taskRoomItem = TaskRoomItem(
                    taskId,
                    mentorFsId,
                    studentFsId,
                    taskTitle,
                    vcbFsDocRefPath,
                    vcbTitle,
                    isAvailable,
                    progress,
                    currentTaskItem,
                    currentTestItem,
                    currentLearningItem,
                    wrongTestAnswers,
                    status
                )
                onSuccess(taskRoomItem)
            }
            .addOnFailureListener {
                Log.d(TAG, "getting task by document reference failed, Error: ${it.message}")
                onSuccess(null)
            }
    }
}