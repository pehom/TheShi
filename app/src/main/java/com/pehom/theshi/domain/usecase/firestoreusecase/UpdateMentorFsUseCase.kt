package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.utils.Constants

class UpdateMentorFsUseCase {
    private val TAG ="UpdateMentorFsUseCase"

    fun execute(
        mentor: MentorRoomItem,
        userFsId: String,
        onSuccess: (Boolean) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val data = mutableMapOf<String, Any>(
            Constants.MENTOR_FS_ID to mentor.mentorFsID,
            Constants.NAME to mentor.name,
            Constants.PHONE_NUMBER to mentor.phone,
            Constants.STATUS to mentor.mentorStatus
        )
        Firebase.firestore.collection(Constants.USERS).document(userFsId).collection(Constants.MENTORS)
            .document(mentor.mentorFsID).update(data)
            .addOnSuccessListener {
                Firebase.firestore.collection(Constants.USERS).document(mentor.mentorFsID).collection(Constants.STUDENTS)
                    .document(userFsId).update(Constants.STATUS, mentor.mentorStatus)
                    .addOnSuccessListener {
                        onSuccess(true)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "updating mentor's side failed, Error: ${it.message}")
                        onSuccess(false)
                    }
            }
            .addOnFailureListener {
                Log.d(TAG, "updating uesr's side failed, Error: ${it.message}")
                onSuccess(false)
            }
    }
}