package com.pehom.theshi.domain.usecase

import androidx.lifecycle.LifecycleOwner
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

class SyncUserTasksUseCase {
    private val TAG = "SyncUserTasksUseCase"

    fun execute(
        user: User,
        lifecycleOwner: LifecycleOwner,
        onSuccess: () -> Unit
        ){
            val roomTaskIds = mutableSetOf<String>()
            val fsTaskIds = mutableSetOf<String>()
            Constants.REPOSITORY.readTaskRoomItemsByFsId(user.fsId.value).observe(lifecycleOwner){
                it.forEach(){item ->
                    roomTaskIds.add(item.id)
                }
            }
            Firebase.firestore.collection(Constants.USERS).document(user.fsId.value).collection(Constants.TASKS).get()
                .addOnSuccessListener { docs ->
                    for (doc in docs){

                    }
                }

    }
}