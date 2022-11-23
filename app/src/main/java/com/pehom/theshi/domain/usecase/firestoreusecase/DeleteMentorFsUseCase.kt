package com.pehom.theshi.domain.usecase.firestoreusecase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.utils.Constants

class DeleteMentorFsUseCase {
    private val TAG = "DeleteMentorFsUseCase"

    fun execute(
        mentor: MentorRoomItem,
        userFsId: String,
        onSuccess: (Boolean) -> Unit
    ){
        var count = 2
        Firebase.firestore.collection(Constants.USERS).document(userFsId).collection(Constants.MENTORS)
            .document(mentor.mentorFsID).update(Constants.STATUS, Constants.DISMISSED)
            .addOnSuccessListener {
                count--
                if (count == 0){
                    onSuccess(true)
                }
            }
            .addOnFailureListener {
                count--
                if (count == 0){
                    onSuccess(false)
                }
            }
        Firebase.firestore.collection(Constants.USERS).document(mentor.mentorFsID).collection(Constants.STUDENTS)
            .document(userFsId).update(Constants.STATUS, Constants.DISMISSED)
            .addOnSuccessListener {
                count--
                if (count == 0){
                    onSuccess(true)
                }
            }
            .addOnFailureListener {
                count--
                if (count == 0){
                    onSuccess(false)
                }
            }
    }
}