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
                    val fundsAmount = doc.get(Constants.FUNDS).toString().toInt()
                    infoUser.authId = doc.get(Constants.AUTH_ID).toString()
                    infoUser.fsId = FsId(fsId)
                    infoUser.email = doc.get( Constants.EMAIL).toString()
                    infoUser.phoneNumber = doc.get(Constants.PHONE_NUMBER).toString()
                    infoUser.funds.setAmount(fundsAmount)
                    infoUser.name = doc[Constants.NAME].toString()
                    infoUser.lastIdSfx = doc[Constants.LAST_TASK_ID_SFX].toString().toInt()
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