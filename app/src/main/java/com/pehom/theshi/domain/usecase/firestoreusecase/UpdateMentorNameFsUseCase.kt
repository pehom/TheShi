package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

class UpdateMentorNameFsUseCase {
    private val TAG = "UpdateMentorFsUseCase"

    fun execute(
        user: User,
        mentor: MentorRoomItem,
        onSuccess: (Boolean) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(user.fsId.value).collection(Constants.MENTORS)
            .document(mentor.mentorFsID).update(Constants.NAME, mentor.name)
            .addOnSuccessListener {
                onSuccess(true)
            }
            .addOnFailureListener {
                Log.d(TAG, "updating mentor's name failed, Error;${it.message}")
                onSuccess(false)
            }
    }
}