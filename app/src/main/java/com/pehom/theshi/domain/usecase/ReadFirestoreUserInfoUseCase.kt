package com.pehom.theshi.domain.usecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Funds
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.*

class ReadFirestoreUserInfoUseCase {
    fun execute(
        fsId: String,
        context: Context,
        onResponse: (User) -> Unit
    ): User {
        val infoUser = User("","","","", mutableListOf(), mutableListOf(), mutableListOf(), Funds())
        Firebase.firestore.collection("Users").document(fsId).get()
            .addOnSuccessListener { doc ->
                Log.d("readFirestoreUserInfo", "fsId = $fsId")
                if (doc.exists()) {
                    val authId = doc.get(Constants.AUTH_ID).toString()
                    val fsId = doc.id
                    val email = doc.get( Constants.EMAIL).toString()
                    val phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                    val tasksString = doc.get(Constants.TASKS).toString()
                    val tasks = tasksFromString(tasksString)
                    val studentsString = doc.get(Constants.STUDENTS).toString()
                    val students = studentsFromString(studentsString)
                    val availableVocabulariesString = doc.get(Constants.AVAILABLE_VOCABULARIES).toString()
                    val availableVocabularies = vocabularyTitlesFromString(availableVocabulariesString)
                    val fundsAmount = doc.get(Constants.FUNDS).toString().toInt()
                    infoUser.authId = authId
                    infoUser.fsId = fsId
                    infoUser.email = email
                    infoUser.phoneNumber = phoneNumber
                    infoUser.tasks += tasks
                    infoUser.students+= students
                    infoUser.availableVocabularies+=availableVocabularies
                    infoUser.funds.setAmount(fundsAmount)
                    onResponse(infoUser)
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