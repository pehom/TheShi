package com.pehom.theshi.domain.usecase

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.presentation.MainActivity
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.*

class SignInUseCase {
   fun execute(
       viewModel: MainViewModel,
       auth: FirebaseAuth,
       loginData: LoginModel){

       auth.signInWithEmailAndPassword(loginData.email, loginData.password)
           .addOnSuccessListener {
               Firebase.firestore.collection("Users").whereEqualTo(Constants.AUTH_ID, auth.currentUser?.uid)
                   .get().addOnSuccessListener {
                       if (it.documents.size == 1) {
                           val doc = it.documents[0]
                           val authId = doc.get(Constants.AUTH_ID).toString()
                           val fsId = doc.get(Constants.FS_ID).toString()
                           val email = doc.get( Constants.EMAIL).toString()
                           val phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                           val tasksString = doc.get(Constants.TASKS).toString()
                           val tasks = tasksFromString(tasksString)
                           val studentsString = doc.get(Constants.STUDENTS).toString()
                           val students = studentsFromString(studentsString)
                           val availableVocabulariesString = doc.get(Constants.AVAILABLE_VOCABULARIES).toString()
                           val availableVocabularies = vocabularyTitlesFromString(availableVocabulariesString)
                           val fundsString = doc.get(Constants.FUNDS).toString()
                           val funds = fundsFromString(fundsString)
                           val createdUser = User(fsId,authId, email, phoneNumber, tasks, students, availableVocabularies, funds)
                           viewModel.user = createdUser
                           viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                       }
                   }
                }
           .addOnFailureListener {
               Log.d("signInUseCase", "failure ${it.message.toString()} ")
           }
   }
}