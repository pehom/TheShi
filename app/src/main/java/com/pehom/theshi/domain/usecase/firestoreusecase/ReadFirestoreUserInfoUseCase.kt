package com.pehom.theshi.domain.usecase.firestoreusecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.utils.*

class ReadFirestoreUserInfoUseCase {
    fun execute(
        fsId: String,
        context: Context,
        onResponse: (User) -> Unit
    ): User {
        val infoUser = User(FsId(""),"","","",  Funds())
        Firebase.firestore.collection("Users").document(fsId).get()
            .addOnSuccessListener { doc ->
                Log.d("readFirestoreUserInfo", "fsId = $fsId")
                if (doc.exists()) {
                    val authId = doc.get(Constants.AUTH_ID).toString()
                    val email = doc.get( Constants.EMAIL).toString()
                    val phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                    val fundsAmount = doc.get(Constants.FUNDS).toString().toInt()
                    infoUser.authId = authId
                    infoUser.fsId = FsId(fsId)
                    infoUser.email = email
                    infoUser.phoneNumber = phoneNumber
                    infoUser.funds.setAmount(fundsAmount)
                    val tasks = mutableListOf<TaskInfo>()
                    val students = mutableListOf<Student>()
                    val availableVocabularies = mutableListOf<VocabularyTitle>()
                   /* Firebase.firestore.collection(Constants.USERS).document(fsId).collection(Constants.TASKS)
                        .get().addOnSuccessListener { result ->
                            if (result.documents.size > 0){
                                for (doc in result.documents) {
                                    val details = doc.data
                                    Log.d("mmm", "readFsUserInfoUseCase task.details = $details")
                                    val currentTaskItem = details?.get(Constants.CURRENT_TASK_ITEM).toString().toInt()
                                    val currentTestItem = details?.get(Constants.CURRENT_TEST_ITEM).toString().toInt()
                                    val currentLearningItem = details?.get(Constants.CURRENT_LEARNING_ITEM).toString().toInt()
                                    val progress = details?.get(Constants.PROGRESS).toString().toInt()
                                    val taskId = details?.get(Constants.TASK_ID).toString()
                                    val title = details?.get(Constants.TASK_TITLE).toString()
                                    val vocabularyId = details?.get(Constants.VOCABULARY_ID).toString().toInt()
                                    val fsDocRefPath = details?.get(Constants.VOCABULARY_FS_DOC_REF_PATH).toString()
                                    val isAvailable = details?.get(Constants.IS_AVAILABLE).toString().toBoolean()
                                    val mentorFsId = details?.get(Constants.MENTOR_FS_ID).toString()
                                    val wrongTestAnswers = details?.get(Constants.WRONG_TEST_ANSWERS) as MutableMap<Int, String>
                                    val taskInfo = TaskInfo(taskId,title,vocabularyTitle)
                                    taskInfo.progress = progress
                                    taskInfo.currentTaskItem = currentTaskItem
                                    taskInfo.currentTestItem = currentTestItem
                                    tasks.add(taskInfo)
                                }
                                Log.d("readFsUserInfo", "tasks.size = ${tasks.size}")
                            }*/
                            Firebase.firestore.collection(Constants.USERS).document(fsId)
                                .collection(Constants.STUDENTS).get()
                                .addOnSuccessListener {result ->
                                    if(result.documents.size>0) {
                                        for (doc in result.documents) {
                                            val fsId = FsId(doc.get(Constants.FS_ID).toString())
                                            val name = doc.get(Constants.NAME).toString()
                                            val learnedWords = doc.get(Constants.LEARNED_WORDS).toString().toInt()
                                            val student = Student(fsId,name)
                                            student.learnedWords = learnedWords
                                            students.add(student)
                                        }
                                    }
                                    Firebase.firestore.collection(Constants.USERS).document(fsId)
                                        .collection(Constants.AVAILABLE_VOCABULARIES).get()
                                        .addOnSuccessListener { result->
                                            if (result.documents.size > 0) {
                                                for (doc in result.documents) {
                                                    val title = doc.get(Constants.VOCABULARY_TITLE).toString()
                                                    val vocTitle = VocabularyTitle(title)
                                                    availableVocabularies.add(vocTitle)
                                                }
                                            }
                                            infoUser.tasks += tasks
                                            infoUser.students += students
                                            infoUser.availableVocabularies += availableVocabularies
                                            onResponse(infoUser)
                                        }
                                        .addOnFailureListener {
                                            Log.d("readFirestoreUserInfoUseCase", "reading available vocabularies failed, Error: ${it.message}")
                                        }
                                }
                                .addOnFailureListener {
                                    Log.d("readFirestoreUserInfoUseCase", "reading students failed, Error: ${it.message}")
                                }
                       /* }
                        .addOnFailureListener {
                            Log.d("readFirestoreUserInfoUseCase", "reading tasks failed, Error: ${it.message}")
                        }*/
                    /*infoUser.tasks += tasks
                    infoUser.students+= students
                    infoUser.availableVocabularies+=availableVocabularies*/

                   // onResponse(infoUser)
                }
                else
                    Log.d("readFirestoreUserInfo", "doc does not exist")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Can't read userInfo", Toast.LENGTH_SHORT).show()
                Log.d("readFirestoreUserInfo", "Error = ${it.message}")
            }
        return infoUser
    }
}