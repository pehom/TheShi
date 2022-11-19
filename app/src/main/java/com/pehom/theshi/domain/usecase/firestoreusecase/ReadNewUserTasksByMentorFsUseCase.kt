package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

class ReadNewUserTasksByMentorFsUseCase {
    private val TAG = "ReadNewUserTasksByMentorFsUseCase"

    fun execute(
        user: User,
        onSuccess: (List<TaskRoomItem>) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
     //   Log.d(TAG, "ReadNewUserTasksByMentorFsUseCase execute() invoked")
        val resultList = mutableListOf<TaskRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(user.fsId.value).collection(Constants.TASKS_BY_MENTOR).get()
            .addOnSuccessListener {
                Log.d(TAG, "querySnapShot = $it")
                val docs = it.documentChanges
                if (docs.isNotEmpty()) {
                    docs.forEachIndexed(){index, doc ->
                        val details = doc.document[Constants.DETAILS] as Map<*,*>
                        val mentorFsId = doc.document[Constants.MENTOR_FS_ID].toString()
                        val status = doc.document[Constants.STATUS].toString()
                        val taskId = details[Constants.TASK_ID].toString()
                        Log.d("mmm", "taskId = $taskId")
                        val taskTitle = details[Constants.TASK_TITLE].toString()
                        val studentFsId = details[Constants.STUDENT_FS_ID].toString()
                        val vcbFsDocRefPath = details[Constants.VOCABULARY_FS_DOC_REF_PATH].toString()
                        val vcbTitle = details[Constants.VOCABULARY_TITLE].toString()
                        val isAvailable = details[Constants.IS_AVAILABLE].toString().toBoolean()
                        val progress = details[Constants.PROGRESS].toString().toInt()
                        val currentTaskItem = details[Constants.CURRENT_TASK_ITEM].toString().toInt()
                        val currentTestItem = details[Constants.CURRENT_TEST_ITEM].toString().toInt()
                        val currentLearningItem = details[Constants.CURRENT_LEARNING_ITEM].toString().toInt()
                        val wrongTestAnswers = details[Constants.WRONG_TEST_ANSWERS] as MutableMap<Int, String>
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
                        resultList.add(taskRoomItem)
                        if (index == docs.size-1) {
                            Log.d(TAG, "ReadNewUserTasksByMentorFsUseCase resultList.size = ${resultList.size}")
                            onSuccess(resultList)
                        }
                    }
                } else {
                    onSuccess(resultList)
                }
            }
    }
}