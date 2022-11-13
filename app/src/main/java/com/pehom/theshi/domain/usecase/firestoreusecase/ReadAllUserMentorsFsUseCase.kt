package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.MentorRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadAllUserMentorsFsUseCase {
    private val TAG = "ReadAllUserMentorsFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: (List<MentorRoomItem>) -> Unit
    ){
        val mentorRoomItems = mutableListOf<MentorRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.MENTORS).get()
            .addOnSuccessListener { docs->
                for (doc in docs) {
                    val mentorFsId = doc[Constants.MENTOR_FS_ID].toString()
                    val mentorName = doc[Constants.NAME].toString()
                    val mentorRoomItem = MentorRoomItem(mentorFsId,mentorName,viewModel.user.value.fsId.value)
                    mentorRoomItems.add(mentorRoomItem)
                }
                onSuccess(mentorRoomItems)
            }
            .addOnFailureListener {
                Log.d(TAG, "reading all user's mentors failed, Error: ${it.message}")
            }
    }
}