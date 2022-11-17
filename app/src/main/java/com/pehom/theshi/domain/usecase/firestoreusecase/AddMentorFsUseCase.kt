package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Mentor
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

class AddMentorFsUseCase {
    private val TAG = "AddMentorFsUseCase"

    fun execute (
        user: User,
        mentor: Mentor,
        onSuccess: () -> Unit
    ){
        Firebase.firestore.collection(Constants.USERS).document(user.fsId.value)
            .collection(Constants.MENTORS).document(mentor.fsId)
            .set(hashMapOf(
                Constants.MENTOR_FS_ID to mentor.fsId,
                Constants.NAME to mentor.name,
                Constants.PHONE_NUMBER to mentor.phoneNumber,
                Constants.STATUS to Constants.ACCEPTED
            ))
            .addOnSuccessListener {
                Firebase.firestore.collection(Constants.USERS).document(user.fsId.value)
                    .collection(Constants.PENDING_REQUESTS).document(mentor.phoneNumber).get()
                    .addOnSuccessListener {
                        it.reference.delete()
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "deleting pending request failed, Error: ${it.message}")
                    }
            }
            .addOnFailureListener {
                Log.d(TAG, "adding mentor failed, Error: ${it.message}")
            }
    }
}