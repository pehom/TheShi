package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.*

class CreateFirestoreAccountUseCase(

) {

    fun execute(viewModel: MainViewModel,
                auth: FirebaseAuth,
                loginData: LoginModel) {
        Log.d(
            "tagg",
            " CreateAccount. execute():  email = ${loginData.email}  password = ${loginData.password}"
        )
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(loginData.email, loginData.password)
            .addOnSuccessListener {
                val user = hashMapOf(
                    Constants.FS_ID to "",
                    Constants.AUTH_ID to auth.currentUser?.uid,
                    Constants.PHONE_NUMBER to loginData.phoneNumber,
                    Constants.EMAIL to loginData.email,
                    Constants.FUNDS to 7,
                    Constants.LAST_TASK_ID_SFX to 0
                )
                db.collection("Users").add(user)
                    .addOnSuccessListener { docRef ->
                        db.collection("Users").document(docRef.id).update(Constants.FS_ID, docRef.id )
                            .addOnSuccessListener {
                                db.collection("Users").document(docRef.id)
                                    .get()
                                    .addOnSuccessListener { doc ->
                                        if (doc.exists()) {
                                            val authId = doc.get(Constants.AUTH_ID).toString()
                                            val fsId = FsId(docRef.id)
                                            val email = doc.get( Constants.EMAIL).toString()
                                            val phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                                            val fundsString = doc.get(Constants.FUNDS).toString()
                                            val funds = fundsFromString(fundsString)
                                            val createdUser = User(fsId,authId, email, phoneNumber, funds)
                                            val lastTaskIdSfx = doc.get(Constants.LAST_TASK_ID_SFX).toString().toInt()
                                            viewModel.user.value = createdUser
                                            viewModel.taskIdFactory = TaskIdFactory(fsId, lastTaskIdSfx, viewModel.sharedPreferences)
                                            viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                                        }
                                    }
                                    .addOnFailureListener {
                                        Log.d("createAccountUseCase", "db.Users reading failed")
                                    }

                            }
                            .addOnFailureListener {
                                Log.d("createAccountUseCase", "db.Users updating fsId failed")
                            }
                    }
                    .addOnFailureListener {
                        Log.d("createAccountUseCase", "db.Users adding failure")
                    }
            }
            .addOnFailureListener {
                Log.d("createAccountUseCase", "failure ${it.message.toString()} ")
                // onFailure(it.message.toString())
            }
    }
}