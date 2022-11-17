package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.domain.model.Mentor
import com.pehom.theshi.utils.Constants

class ReadNewUserMentorsFsUseCase {
    private val TAG = "ReadNewUserMentorsFsUseCase"

    fun execute(
        userFsId: String,
        onSuccess: (List<MentorRoomItem>) -> Unit
    ){
        val resultList = mutableListOf<MentorRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(userFsId).collection(Constants.MENTORS).get()
            .addOnSuccessListener {
                val docs = it.documentChanges
                docs.forEach(){doc ->
                    val mentor = MentorRoomItem(
                        doc.document[Constants.MENTOR_FS_ID].toString(),
                        doc.document[Constants.NAME].toString(),
                        doc.document[Constants.PHONE_NUMBER].toString(),
                        userFsId

                    )
                    resultList.add(mentor)
                }
                onSuccess(resultList)
            }
            .addOnFailureListener {
                Log.d(TAG, "reading new mentors failed, Error: ${it.message}")
            }
    }
}