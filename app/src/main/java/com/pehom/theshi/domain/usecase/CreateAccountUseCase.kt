package com.pehom.theshi.domain.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.presentation.viewmodel.MainViewModel

class CreateAccountUseCase(
    private val viewModel: MainViewModel,
    private val auth: FirebaseAuth,
    private val loginData: LoginModel
) {

    fun execute() {
        Log.d(
            "tagg",
            " CreateAccount. execute():  email = ${loginData.email}  password = ${loginData.password}"
        )
        auth.createUserWithEmailAndPassword(loginData.email, loginData.password)
            .addOnSuccessListener { viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN }
            .addOnFailureListener {
                Log.d("createAccountUseCase", "failure ${it.message.toString()} ")
                // onFailure(it.message.toString())
            }
    }
}