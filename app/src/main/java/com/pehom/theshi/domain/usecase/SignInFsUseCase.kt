package com.pehom.theshi.domain.usecase

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.*

class SignInFsUseCase {
    private val TAG = "SignInFsUseCase"
    fun execute(
        /*context: Context,
        viewModel: MainViewModel,*/
        auth: FirebaseAuth,
        loginData: LoginModel,
        onSuccess: (Boolean) -> Unit
        )
   {
       Log.d(TAG, "$TAG invoked")
       if (isNetworkAvailable()){
           auth.signInWithEmailAndPassword(loginData.email, loginData.password)
               .addOnSuccessListener {
                   onSuccess(true)
               }
               .addOnFailureListener {
                   onSuccess(false)
                   Log.d("signInUseCase", "Signing in  firestore failed, Error:  ${it.message.toString()} ")
               }
       } else {
           onSuccess(false)
       }
   }
}