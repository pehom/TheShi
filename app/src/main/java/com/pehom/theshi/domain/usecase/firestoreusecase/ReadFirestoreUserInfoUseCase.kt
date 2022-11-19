package com.pehom.theshi.domain.usecase.firestoreusecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.utils.*

class ReadFirestoreUserInfoUseCase {
    private val TAG = "ReadFirestoreUserInfoUseCase"
    fun execute(
        fsId: String,
        context: Context,
        onSuccess: (User) -> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        val infoUser = User(FsId(""),"","","",  Funds())
        Firebase.firestore.collection("Users").document(fsId).get()
            .addOnSuccessListener { doc ->
                Log.d("readFirestoreUserInfo", "fsId = $fsId")
                if (doc.exists()) {
                    val authId = doc.get(Constants.AUTH_ID).toString()
                    val email = doc.get( Constants.EMAIL).toString()
                    val phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                    val fundsAmount = doc.get(Constants.FUNDS).toString().toInt()
                    val name = doc[Constants.NAME].toString()
                    infoUser.authId = authId
                    infoUser.fsId = FsId(fsId)
                    infoUser.email = email
                    infoUser.phoneNumber = phoneNumber
                    infoUser.funds.setAmount(fundsAmount)
                    infoUser.name = name
                    onSuccess(infoUser)
                }
                else
                    Log.d("readFirestoreUserInfo", "doc does not exist")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Can't read userInfo", Toast.LENGTH_SHORT).show()
                Log.d("readFirestoreUserInfo", "Error = ${it.message}")
            }
    }
}